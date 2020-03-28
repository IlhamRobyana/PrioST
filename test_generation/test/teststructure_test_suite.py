import unittest

from test_generation.test_suite import TestCase
from model.alts import Transition, State


class TestTestCase(unittest.TestCase):
    def test_empty_testcase(self):
        tc = TestCase('empty')
        self.assertEqual(len(tc), 0)
        self.assertEqual(tc.name, 'empty')

    def test_contains(self):
        tc = TestCase('simple')
        t1 = Transition(State('id1', '0'), 'simple_id_1', 'First step of the TC', State('id2', '1'), 'step')
        tc.insert(t1)

        t_equal = Transition(State('id1', '0'), 'simple_id_1', 'First step of the TC', State('id2', '1'), 'step')
        self.assertTrue(t_equal in tc)

        transition_different = Transition(State('id5', '5'), 'simple_id_2', 'First step of the TC', State('id2', '1'), 'step')
        self.assertTrue(transition_different not in tc)

    def test_type(self):
        tc = TestCase('simple')
        self.assertEqual(len(tc), 0)
        tc.insert('Wrong Object')
        self.assertEqual(len(tc), 0)

if __name__ == '__main__':
    unittest.main()
