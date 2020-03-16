import unittest

from model.test.alts_test_suite import TestState, TestALTS, TestTransition

models_test_suite = unittest.TestSuite([
    unittest.TestLoader().loadTestsFromTestCase(TestState),
    unittest.TestLoader().loadTestsFromTestCase(TestTransition),
    unittest.TestLoader().loadTestsFromTestCase(TestALTS)
])


def run_tests():
    result = unittest.TestLoader()
    runner = unittest.TextTestRunner()
    print(runner.run(models_test_suite))


if __name__ == '__main__':
    run_tests()
