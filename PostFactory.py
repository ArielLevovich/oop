from Post import Post


class TextPost(Post):
    def __init__(self, owner, content):
        super().__init__(owner, content)

    def __str__(self):
        return f"{self.get_owner().get_username()} published a post:\n\"{self.get_content()}\"\n"



import matplotlib.pyplot as plt
import matplotlib.image as mpimg


class ImagePost(Post):
    def __init__(self, owner, content):
        super().__init__(owner, content)

    def __str__(self):
        return f"{self.get_owner().get_username()} posted a picture\nImage Path: {self.get_content()}\n"

    def display(self):
        # Load and display the image using matplotlib
        img = mpimg.imread(self.get_content())
        plt.imshow(img)
        plt.axis('off')  # Hide axis labels
        plt.show()


class SalePost(Post):
    def __init__(self, owner, content, price, place):
        super().__init__(owner, content)
        self.price = price
        self.place = place
        self.is_available = True

    def sold(self, password):
        if self.get_owner().get_password() == password:
            self.is_available = False
            print(f"{self.get_owner().get_username()}'s product is sold")

    def discount(self, discount_percentage, password):
        if self.get_owner().get_password() == password:
            self.price *= (1 - discount_percentage / 100)
            print(f"Discount on {self.get_owner().get_username()} product! the new price is {self.price}")

    def __str__(self):
        if self.is_available:
            return (f"{self.get_owner().get_username()} posted a product for sale:\n"
                    f"For sale! {self.get_content()}, price: {self.price}, pickup from: {self.place}\n")
        return (f"{self.get_owner().get_username()} posted a product for sale:\n"
                f"Sold! {self.get_content()}, price: {self.price}, pickup from: {self.place}\n")


class PostFactory:
    @staticmethod
    def create_post(owner, type, content, price=None, place=None):
        """
        :rtype: Post
        """
        if type == "Text":
            p = TextPost(owner, content)
            print(p)
            return p
        elif type == "Image":
            p = ImagePost(owner, content)
            print(p)
            return p
        elif type == "Sale":
            p = SalePost(owner, content, price, place)
            print(p)
            return p
        raise ValueError("Unknown post type")

# Alice published a post:
# "In 1492, Christopher Columbus set sail,
# hoping to find a westward route to Asia, but instead,
# he discovered the Americas, changing the course of history forever."
#
# David posted a picture
#
# Charlie posted a product for sale:
# For sale! Toyota prius 2012, price: 42000, pickup from: Haifa
