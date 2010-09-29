(ns fleet.runtime-test
  (:require fleet.util)
  (:use
    clojure.test
    fleet.runtime))

(def escaping-fn
  fleet.util/escape-xml)

(deftest raw-test
  (let [s "qwe<br>asd"]
    (is (not (raw? str s)))
    (is (raw? str (raw str s)))))


(deftest different-fns-raw-test
  (let [s "qwe<br>asd"]
    (is (raw? escaping-fn (raw escaping-fn s)))
    (is (not (raw? escaping-fn (raw str s))))))

(deftest screen-obj-test
  (let [s "qwe<br>asd"]
    (is (=
      (screen escaping-fn s)
      "qwe&lt;br&gt;asd"))
    (is (=
      (screen escaping-fn (raw escaping-fn s))
      "qwe<br>asd"))
    (is (raw? escaping-fn (screen escaping-fn s)))
    (is (raw? escaping-fn (screen escaping-fn (raw escaping-fn s))))))

(deftest screen-seq-test
  (let [s ["qwe<br>asd" " " (raw escaping-fn "qwe<br>asd")]]
    (is (=
      (screen escaping-fn s)
      "qwe&lt;br&gt;asd qwe<br>asd"))
    (is (raw?  escaping-fn (screen escaping-fn s)))))
