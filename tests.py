from model import tests as model
from parser import tests as parser
from test_generation import tests as tcm


import unittest

all_tests = unittest.TestSuite([
    model.models_test_suite,
    parser.parser_tests,
    tcm.teststructure_test_suite
])


def run_all_suites():
    result = unittest.TestLoader()
    runner = unittest.TextTestRunner()
    print(runner.run(all_tests))


if __name__ == '__main__':
    run_all_suites()
