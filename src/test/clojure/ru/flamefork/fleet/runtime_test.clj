(ns ru.flamefork.fleet.runtime-test
  (:use
    [clojure.test]
    [ru.flamefork.fleet.runtime]))

(def f @ru.flamefork.fleet/escape-fn)

(deftest raw-test
  (let [s "qwe<br>asd"]
    (is (not (raw? s)))
    (is (raw? (raw s)))))

(deftest screen-obj-test
  (let [s "qwe<br>asd"]
    (is (=
      (screen f s)
      "qwe&lt;br&gt;asd"))
    (is (=
      (screen f (raw s))
      "qwe<br>asd"))
    (is (raw? (screen f s)))
    (is (raw? (screen f (raw s))))))

(deftest screen-seq-test
  (let [s ["qwe<br>asd" " " (raw "qwe<br>asd")]]
    (is (=
      (screen f s)
      "qwe&lt;br&gt;asd qwe<br>asd"))
    (is (raw? (screen f s)))))
