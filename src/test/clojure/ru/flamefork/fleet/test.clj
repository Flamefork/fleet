(ns ru.flamefork.fleet
  (:use clojure.test))

(deftest basics
  (println (fleet
"<p><(post :body)></p>
<ul>
  <(for [tag (post :tags] \">
    <li><(tag)></li>
  <\")>
</ul>")))
