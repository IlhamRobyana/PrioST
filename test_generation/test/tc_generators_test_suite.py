import unittest

from test_generation.generators import TestCaseGenerator
from parser.tgf_parser import TGFParser
import os
from test_generation.test import root_dir


class TestTestCaseGeneration(unittest.TestCase):
    def test_all1loop_path(self):
        alts = TGFParser().parse(os.path.join(root_dir, 'resources/tgf/ConditionStepResult.tgf'))
        ts = TestCaseGenerator().all_n_loop(alts, n=0)
        self.assertEqual(len(ts), 2)


if __name__ == '__main__':
    unittest.main()
