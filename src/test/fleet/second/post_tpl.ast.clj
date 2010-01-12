[:tpl (
  [:text "<p>"]
  [:embed (
    [:clj "post :body"])]
  [:text "</p>\n<ul>\n  "]
  [:embed (
    [:clj "for [tag (post :tags)] "]
    [:tpl (
      [:text "\n    <li>"]
      [:embed (
        [:clj "str tag"])]
      [:text "</li>\n  "])])]
  [:text "\n</ul>"])]