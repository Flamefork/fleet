(do
  (use 'ru.flamefork.fleet.runtime)
  (fn [post]
    (apply str [
      "<p>" (apply str (post :body)) "</p>\n<ul>\n  "
      (apply str (for [tag (post :tags)]
        (apply str [
          "\n    <li>" (apply str (str tag)) "</li>\n  "])))
      "\n</ul>"])))
