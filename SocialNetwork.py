from User import User


class SocialNetwork:
    _instance = None

    def __new__(cls, name=None):
        if cls._instance is None:
            cls._instance = super(SocialNetwork, cls).__new__(cls)
            cls._instance.name = name  # Set the name during the first instantiation.
        return cls._instance

    def __init__(self, name=None):
        if not hasattr(self, 'initialized'):  # Check if the instance is already initialized.
            self.users = {}  # Initialize users dictionary.
            # self.usernames = set()  # Initialize usernames set.
            self.initialized = True  # Mark as initialized to prevent re-initialization.
            print(f"The social network {name} was created!")

    def sign_up(self, username, password):
        if username in self.users:
            print("Username already exists.")
            return None

        user = User(username)
        self.users[username] = user
        if password is not None and len(password) > 0 and 4 < len(password) < 8:
            user.set_password(password)
        return user

    def log_out(self, username):
        if username in self.users:
            self.users[username].set_is_connected(False)
            print(f"{username} disconnected.")

    def log_in(self, username, password):
        if username in self.users:
            if self.users[username].check_password(password):
                self.users[username].set_is_connected(True)
                print(f"{username} connected.")
            else:
                print("Password is incorrect.")
        else:
            print("User doesn't exist, you must sign up.")

    def __str__(self):
        output = f"{self.name} social network:\n"
        for user in self.users.values():
            output += (f"User name: {user.get_username()}, "
                       f"Number of posts: {len(user.get_posts())}, "
                       f"Number of followers: {len(user.get_followers())}\n")
        return output.strip()