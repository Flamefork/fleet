(ns fleet.runtime-test
  (:require clojure.contrib.lazy-xml)
  (:use
    [clojure.test]
    [fleet.runtime]))

(def escape-fn clojure.contrib.lazy-xml/escape-xml)

(deftest raw-test
  (let [s "qwe<br>asd"]
    (is (not (raw? s)))
    (is (raw? (raw s)))))

(deftest screen-obj-test
  (let [s "qwe<br>asd"]
    (is (=
      (screen escape-fn s)
      "qwe&lt;br&gt;asd"))
    (is (=
      (screen escape-fn (raw s))
      "qwe<br>asd"))
    (is (raw? (screen escape-fn s)))
    (is (raw? (screen escape-fn (raw s))))))

(deftest screen-seq-test
  (let [s ["qwe<br>asd" " " (raw "qwe<br>asd")]]
    (is (=
      (screen escape-fn s)
      "qwe&lt;br&gt;asd qwe<br>asd"))
    (is (raw? (screen escape-fn s)))))
