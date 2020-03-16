import unittest

from model.alts import *

class TestState(unittest.TestCase):
    def test_empty_state(self):
        try:
            clean_state = State()
        except TypeError:
            self.assertTrue(True)

    def test_state_creation(self):
        state = State('label1')
        self.assertEqual(state.label, 'label1')


class TestTransition(unittest.TestCase):
    def test_empty_transition(self):
        try:
            transition = Transition()
        except TypeError:
            self.assertTrue(True)

    def test_transition_creation_without_type(self):
        from_state = State('0')
        to_state = State('1')
        transition_label = 'Action Performed'
        transition = Transition(from_state, transition_label, to_state)
        representation = '0 -> Action Performed -> 1'
        self.assertTrue(repr(transition), '')

    def test_transition_creation_full(self):
        from_state = State('0')
        to_state = State('1')
        transition_label = 'Action Performed'
        transition_type = 'step'  # Assuming it could be in [step, condition, expected_result]
        transition = Transition(from_state, transition_label, to_state, transition_type)
        representation = '0 -> (step) Action Performed -> 1'
        self.assertTrue(repr(transition), '')


class TestALTS(unittest.TestCase):
    def test_empty_alts(self):
        alts = ALTS('empty')
        self.assertEqual(alts.name, 'empty')
        self.assertIsNone(alts.initial_state)
        self.assertDictEqual(alts.states, {})
        self.assertDictEqual(alts.transitions, {})

    def test_simple_alts(self):
        alts = ALTS('simple')
        state0 = State('0')
        state1 = State('1')
        transition_1 = Transition(state0, 'Run the test command', state1, 'step')
        alts.add_transition(transition_1)
        state2 = State('2')
        transition2 = Transition(state1, 'The user must see a message on the output', state2, 'result')
        alts.add_transition(transition2)
        self.assertEqual(alts.name, 'simple')
        self.assertEqual(alts.initial_state.label, '0')
        self.assertEqual(len(alts.states), 3)
        self.assertEqual(len(alts.transitions), 2)


if __name__ == '__main__':
    unittest.main()
