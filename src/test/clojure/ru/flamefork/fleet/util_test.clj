(ns ru.flamefork.fleet.util-test
  (:use
    [clojure.test]
    [ru.flamefork.fleet.util]))

(deftest bypass-test
  (let [obj (java.lang.Object.)]
    (is (= obj (bypass obj)))))

(deftest escape-clj-string-test
  (is (=
    (escape-clj-string "asd\"qwe\\zxc\n")
    "asd\\\"qwe\\\\zxc\n")))

(deftest escape-string-test
  (is (=
    (escape-string "asd\"qwe\\zxc\n")
    "asd\\\"qwe\\\\zxc\\n")))
