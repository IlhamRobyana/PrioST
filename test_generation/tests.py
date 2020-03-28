import unittest

from test_generation.test.teststructure_test_suite import TestTestCase
from test_generation.test.tc_generators_test_suite import TestTestCaseGeneration

teststructure_test_suite = unittest.TestSuite([
    unittest.TestLoader().loadTestsFromTestCase(TestTestCase),
    unittest.TestLoader().loadTestsFromTestCase(TestTestCaseGeneration)
])


if __name__ == '__main__':
    unittest.main()
