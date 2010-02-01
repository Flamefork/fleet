(ns fleet.util-test
  (:use
    [clojure.test]
    [fleet.util])
  (:import
    [fleet.util CljString]))


(deftest clj-string-with-meta-test
  (let [asd (CljString. "test")
        asd-w-meta (with-meta asd {:asd 123})]
    (is (= (meta asd-w-meta) {:asd 123}))
    (is (= asd-w-meta asd))))

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

(deftest escape-regex-test
  (let [s "^\\.([?*|+$]).//^"]
    (is (-> s
      escape-regex
      re-pattern
      (re-matches s)))))
