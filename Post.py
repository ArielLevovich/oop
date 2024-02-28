from Subject import Subject


class Post:
    def __init__(self, owner, content):
        """

        :type owner: user
        """
        super().__init__()
        self.__content = content
        self.__owner = owner
        self.__subject = Subject()
        self.__subject.attach(owner)  # Make the author an observer of their post

    def get_subject(self):
        return self.__subject

    def get_content(self):
        return self.__content

    def get_owner(self):
        return self.__owner

    def like(self, user):
        if user != self.__owner and self.__owner.get_is_connected:
            # Notify the post's author about the like
            self.__subject.notify(f"{user.get_username()} liked your post")

    def comment(self, user ,comment):
        if user != self.__owner and self.__owner.get_is_connected:
            # Notify the post's author about the like
            self.__subject.notify(f"{user.get_username()} commented on your post: " + comment)

    #def print(self):

