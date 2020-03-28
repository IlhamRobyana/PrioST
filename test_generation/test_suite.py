from collections import UserList
from model.alts import *


class TestCase(UserList):

    def __init__(self, name='', transition_list=None):
        super().__init__(initlist=transition_list)
        self.name = name

    def insert(self, transition, position=-1):
        if isinstance(transition, Transition):
            super().insert(position, transition)


class TestSuite(UserList):

    def __init__(self, name='', test_case_list=None):
        super().__init__(test_case_list)
        self.name = name

    def insert(self, test_case, position=-1):
        if isinstance(test_case, TestCase):
            super().insert(position, test_case)
