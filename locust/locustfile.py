import random
import string

from locust import HttpUser, task, constant


def generate_text(length=8):
    # 영문자 소문자와 숫자 조합으로 username 생성
    characters = string.ascii_lowercase + string.digits
    username = ''.join(random.choice(characters) for _ in range(length))
    return username


class CommonUser(HttpUser):
    # TODO: 여기서 본인 EC2 IP 바꿔주세요
    host = "http://43.200.2.206:8080"
    wait_time = constant(5)

    @task
    def hello_world(self):
        headers = {"Content-Type": "application/json"}

        # 회원 가입 후 로그인하여 JWT 토큰 생성            -> MySQL
        username = generate_text()
        password = "1q2w3e4r!!"
        res = self.client.post("/api/users/signUp", headers=headers, json={
            "username": username, "password": password, "email": "test@test.com"
        })
        res = self.client.post(f"/api/users/login?username={username}&password={password}", headers=headers)
        token = res.text
        headers["Authorization"] = "Bearer " + token

        # 게시글(+광고) 리스트 1회 조회 후 게시글 1회 작성(광고도 하나 보고 클릭)         -> MySQL
        board_id = 1
        res = self.client.get(f"/api/boards/{board_id}/articles", headers=headers)
        articles = res.json()
        res = self.client.get(f"/api/ads", headers=headers)
        ads = res.json()
        if ads:
            ad_id = random.choice(ads)["id"]
            # 광고 보기
            self.client.get(f"/api/ads/{ad_id}", headers=headers)
            self.client.get(f"/api/ads/{ad_id}?isTrueView=true", headers=headers)
            # 광고 클릭
            self.client.post(f"/api/ads/{ad_id}", headers=headers)
        # 게시글 하나 작성
        self.client.post(f"/api/boards/{board_id}/articles", headers=headers, json={
            "title": generate_text(100),
            "content": generate_text(200)
        })
        # 게시글 중에 랜덤하게 하나 댓글 달기
        if articles:
            article_id = random.choice(articles)["id"]
            self.client.post(f"/api/boards/{board_id}/articles/{article_id}/comments", headers=headers, json={
                "content": generate_text(200)
            })

        # 게시글 알림 리스트 1회 조회 후 모든 알림 읽음 처리  -> MongoDB / RabbitMQ
        res = self.client.get(f"/api/users/history", headers=headers)
        history_list = res.json()
        if history_list:
            for history in history_list:
                # 모든 알림 읽음 처리
                if not history["isRead"]:
                    history_id = history["id"]
                    notice_id = history["noticeId"]
                    if notice_id == 0:
                        self.client.post(f"/api/users/history?historyId={history_id}", headers=headers)
                    else:
                        self.client.get(f"/api/notices/{notice_id}", headers=headers)

        # 게시글 검색(search) 1회 후 인기글 10회 조회     -> ElasticSearch / Redis
        keyword = generate_text(10)
        self.client.get(f"/api/boards/{board_id}/articles/search?keyword={keyword}", headers=headers)
        # 인기글 설정 필요
        # self.client.get(f"/api/boards/{board_id}/articles/1", headers=headers)

        # 공지사항 1분마다 작성                         -> MySQL / MongoDB / RabbitMQ
        self.client.post(f"/api/notices", headers=headers, json={
            "title": generate_text(100),
            "content": generate_text(200)
        })
