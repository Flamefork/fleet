(ns ru.flamefork.fleet.clj-string-test
  (:use
    [clojure.test])
  (:import
    [ru.flamefork.util CljString]))

(deftest with-meta-test
  (let [asd (CljString. "test")
        asd-w-meta (with-meta asd {:asd 123})]
    (is (= ^asd-w-meta {:asd 123}))
    (is (= asd-w-meta asd))))
