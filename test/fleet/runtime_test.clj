(ns fleet.runtime-test
  (:require clojure.contrib.lazy-xml)
  (:use
    [clojure.test]
    [fleet.runtime]))

(def escape-fn clojure.contrib.lazy-xml/escape-xml)

(deftest raw-test
  (let [s "qwe<br>asd"]
    (is (not (raw? str s)))
    (is (raw? str (raw str s)))))


(deftest different-fns-raw-test
  (let [s "qwe<br>asd"]
    (is (raw? escape-fn (raw escape-fn s)))
    (is (not (raw? escape-fn (raw str s))))))

(deftest screen-obj-test
  (let [s "qwe<br>asd"]
    (is (=
      (screen escape-fn s)
      "qwe&lt;br&gt;asd"))
    (is (=
      (screen escape-fn (raw escape-fn s))
      "qwe<br>asd"))
    (is (raw? escape-fn (screen escape-fn s)))
    (is (raw? escape-fn (screen escape-fn (raw escape-fn s))))))

(deftest screen-seq-test
  (let [s ["qwe<br>asd" " " (raw escape-fn "qwe<br>asd")]]
    (is (=
      (screen escape-fn s)
      "qwe&lt;br&gt;asd qwe<br>asd"))
    (is (raw?  escape-fn (screen escape-fn s)))))
