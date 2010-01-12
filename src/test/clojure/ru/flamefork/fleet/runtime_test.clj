(ns ru.flamefork.fleet.runtime-test
  (:use
    [clojure.test]
    [ru.flamefork.fleet.runtime]))

(deftest raw-test
  (let [s "qwe<br>asd"]
    (is (not (raw? s)))
    (is (raw? (raw s)))))

(deftest screen-obj-test
  (let [s "qwe<br>asd"]
    (is (=
      (screen s)
      "qwe&lt;br&gt;asd"))
    (is (=
      (screen (raw s))
      "qwe<br>asd"))
    (is (raw? (screen s)))
    (is (raw? (screen (raw s))))))

(deftest screen-seq-test
  (let [s ["qwe<br>asd" " " (raw "qwe<br>asd")]]
    (is (=
      (screen s)
      "qwe&lt;br&gt;asd qwe<br>asd"))
    (is (raw? (screen s)))))
