from PostFactory import PostFactory
from Subject import Subject


class User:
    def __init__(self, username):
        super().__init__()
        self.__password = None
        self.__username = username
        self.__followers = set()
        self.__posts = []
        self.__isConnected = True
        self.__subject = Subject()
        self.__notifications = []
    def get_username(self):
        return self.__username

    def get_password(self):
        return self.__password

    def get_is_connected(self):
        return self.__isConnected

    def set_is_connected(self, is_connected):
        self.__isConnected = is_connected

    def get_followers(self):
        return self.__followers
    def get_posts(self):
        return self.__posts
    def get_subject(self):
        return self.__subject
    def follow(self, other_user):
        if not self.__isConnected:
            print("you are logged out")
        else:
            if self.__username not in [user.__username for user in other_user.get_followers()]:
                other_user.addFollower(self)
                other_user.get_subject().attach(self)
                print(f"{self.__username} started following {other_user.__username}")
            else:
                print(f"{self.__username} is already following {other_user.__username}")

    def unfollow(self, other_user):
        if not self.__isConnected:
            print("you are logged out")
        else:
            if self.__username in [user.__username for user in other_user.get_followers()]:
                other_user.get_followers().remove(self)
                other_user.get_subject().detach(self)
                print(f"{self.__username} unfollowed {other_user.__username}")
            else:
                print(f"{self.__username} is not following {other_user.__username}")

    def set_password(self, password):
        if not self.__isConnected:
            print("you are logged out")
        else:
            assert isinstance(password, str)
            self.__password = password

    def update(self, message):
        if not message.endswith("has a new post"):
            print(f"notification to {self.__username}: {message}")

        if "commented on your post:" in message:
            truncated_text = self.truncate_from_character(message)
            self.__notifications.append(f"{truncated_text}")
        else:
            self.__notifications.append(f"{message}")

    def publish_post(self, type, content, price=None, place=None):
        if not self.__isConnected:
            print("you are logged out")
        else:
            if content is not None and type is not None:
                p = PostFactory.create_post(self, type, content, price, place)
                self.__posts.append(p)
                self.__subject.notify(f"{self.__username} has a new post")
                return p
            else:
                print("can't make post without content and type parameters")
                return None

    def print_notifications(self):
        print(f"{self.__username}'s notifications:")
        for notification in self.__notifications:
            print(notification)

    def check_password(self, password):
        return self.__password == password

    def addFollower(self, user):
        self.__followers.add(user)

    def truncate_from_character(self, text, character=":"):
        # Find the position of the character
        position = text.find(character)

        # If the character is found, truncate the string up to that position
        if position != -1:
            return text[:position]
        else:
            # If the character is not found, return the original string
            return text
    def __str__(self):
        return f"User name: {self.get_username()}, Number of posts: {len(self.get_posts())}, Number of followers: {len(self.get_followers())}"