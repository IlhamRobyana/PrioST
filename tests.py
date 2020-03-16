from model import tests as model

import unittest

all_tests = unittest.TestSuite([
    model.models_test_suite
])


def run_all_suites():
    result = unittest.TestLoader()
    runner = unittest.TextTestRunner()
    print(runner.run(all_tests))


if __name__ == '__main__':
    run_all_suites()
