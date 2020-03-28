from parser.test.tgfparser_test_suite import TestTGFParser

import unittest

parser_tests = unittest.TestSuite([
    unittest.TestLoader().loadTestsFromTestCase(TestTGFParser)
])


def run_tests():
    result = unittest.TestLoader()
    runner = unittest.TextTestRunner()
    print(runner.run(parser_tests))


if __name__ == '__main__':
    run_tests()
