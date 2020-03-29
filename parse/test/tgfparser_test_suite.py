import unittest
from parse.tgf_parser import TGFParser
from parse.test import root_dir
import os


class TestTGFParser(unittest.TestCase):
    def test_simple_tgf(self):
        file = os.path.join(root_dir, 'resources/tgf/ConditionStepResult.tgf')
        parser = TGFParser()
        alts = parser.parse(file)
        self.assertEqual(len(alts.states), 6)
        self.assertEqual(len(alts.transitions), 5)


if __name__ == '__main__':
    unittest.main()
