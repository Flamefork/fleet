(ns ru.flamefork.fleet
  (:use
    [clojure.test]
    [clojure.contrib.pprint]
    [clojure.contrib.def]))

(def test-post
  {:body "Body" :tags ["tag1" "tag2" "tag3"]})

(deftest parse-test
  (println (parse
"<p><(post :body)></p>
<ul>
  <(for [tag (post :tags)] \">
    <li><(str tag)></li>
  <\")>
</ul>")))

(deftest compilation
  (println ((fleet '(post title)
"<p><(str title)></p>
<p><(post :body)></p>
<ul>
  <(for [tag (post :tags)] \">
    <li><(str tag)></li>
  <\")>
</ul>") test-post "Post Template")))

(deftest template
  (deftemplate single-post
    [post] "<p><(post :body)></p>")
  (println (single-post test-post)))