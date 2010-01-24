# Fleet

Fleet is a FLExiblE Templates for Clojure.

## Gist

0. Template is function of its arguments.
0. HTML is better for HTML than some host language DSL (just cause HTML *is* DSL).
0. DOM manipulating and XSLT are not good for templating (yes, opinionated).
0. Clojure is good :)
0. HTML isn't the only language that needs templating.

## Why Fleet?

Because  
— I wanted template engine for Clojure  
— close to Clojure  
— and I hate writing HTML not in HTML (clj-html, haml, etc).

So I've started by reviewing few Clojure and CL implementations of ERB/JSP-like templates. Their syntax looks like  
`<p><%= (post :body) %></p>`  
or  
`<p><?clj (post :body) ?></p>`  
Also, in order to prevent XSS attacks, one needs to append some `escape-xml` function call to all this code:  
`<p><%= (escape-html (post :body)) %></p>` or (Rails-like) `<p><%=(h(post :body))%></p>`  
10 chars `<%=(h())%>` just to safely insert expression value seems to be a little too much...

Then I realized that the following syntax is usable:  
`<p><(post :body)></p>`  
Not a big deal, but... that's all. Really, `<%= (escape-html ...` is here, and all other constructions
are here too.

Rails-like partials like  
`<%= render :partial => 'post', :collection => posts %>`  
are here too:  
`<(map post-tpl posts)>`  
...just plain Clojure code.

Need to bypass escaping?  
`<(raw "<script>alert('Hello!')</script>")>`

Not writing HTML at all? Changing `escape-fn` to e.g. `str` or `(fn [o] o)` will disable escaping.  
Changing to some `escape-js` will, obviously, work for JSON or JS.

## Roadmap

0. `DONE` Language design
0. `DONE` API design
0. `DONE` Parser
0. `DONE` Builder
0. `DONE` Infrastructure
0. `DONE` Auto HTML-escaping
0. `DONE` Anonymous templates
0. `IN PROGRESS` Recursive load/register templates in specified path
0. `IN PROGRESS` Language contexts: different escaping functions, inflected from filename/ext (e.g. post.html.fleet and post.json.fleet)
0. (?) Get rid of antlr dependency (reimplement parser)
0. Support escaping of Fleet tokens (like \<( to bypass parsing it)
0. Cleanup

`DONE` = First rough version.

## API

`(fleet [& args] template-str escape-fn)`  
Creates anonymous function from template-str applying escaping by escape-fn.

`(fleet-ns root-path filters)`  
Treats root-path as root of template namespaceand creates template function for each file in it with name according to relative path.
Template function in this case takes one or two arguments: first named same as function name and second named "data".
When it's called with one arguments both symbols (fn-name and data) are bound to same value (argument value).

Filters argument is map of file-mask -> fn pairs used to filter which files to process and with which escaping function.
Masks could be defined as :keyword or as #"regex".  
:default mask is treated as "others". If it is set all files (escept for .hidden ones) will be processed. 

## Template Language

Main Fleet construction is Spaceship `<()>`, just cause (star)fleet consists of many spaceships.

`<()>` is almost equivalent to `()`, so
`<h1><(body)></h1>` in Fleet is nearly the same as `(str "<h1>" (body) "</h1>")` in Clojure.

The only difference is that `(body)` output gets html-encoded to prevent XSS.
Use `raw` function to prevent encoding: `<(raw "<br/>")>`.
Use `str` function to place value `<(str posts-count)>`.

This seems to be complete system, but writing something like
    <(raw (for [p posts]
      (str "<li class=\"post\">" (p :title) "</li>")))>
is too ugly.. And defining `<li class="post"><(p :title)></li>` as separate template
can be overkill in many cases. So there should be the way of embedding strings and anonymous templates.

Slipway construction `"><"` intended for embedding strings.
The previous example could be rewritten using Slipway as
    <(for [p posts] ">
      <li class="post"><(p :title)></li>
    <")>

This example has two points worth mentioning.
Result of `"><"` construction processing is expression with String type.
Strings in Slipway concidered `raw` by default.

Next case is something like this:
    <(raw (map (fn [post]
      (str "<li class=\"post\">" (post :title) "</li>")) posts))>

With Slipway it can be replaced with
    <(map (fn [post] ">
      <li class="post"><(post :title)></li>
    <") posts)>

Need to mention that all this supports lexical scoping and other Clojure fectures just like reference (previous) expression.

## Examples

### Language

Template file (`post_dedicated.fleet`):
    <head>
      <title><(post :title)></title>

      <(stylesheet :main)>
      <(raw "<script>alert('Hello!')</script>")>
    </head>
    <body>
    
    <p><(str notice)></p>
    
    <(; Begin of post)>
    <(inside-frame (let [p post] ">
      Author: <(p :author)><br/>
      Date: <(p :date)><br/>
    <"))>

    <p><(post :body)></p>
    <ul>
      <(for [tag (post :tags] ">
        <li><(str tag)></li>
      <")>
    </ul>
    <(; End of post)>

    <(footer)>
    </body>
    </html>

Clojure:
    (deftemplate post-page [post] "post_dedicated")
    
    (footer)

    (post-page p)

### API

Low-level:
    (def footer (fleet "<p>&copy; <(.get (Calendar/getInstance) Calendar/YEAR)> Flamefork</p>"))

High-level:

Directory tree
    root_dir/
      first_subdir/
        file_a.html.fleet
        file_b.html.fleet
      second_subdir/
        file_c.html.fleet
will be treated and processed by `(fleet-ns "path/to/root_dir" {...})` as functions
    first-subdir.file-a
    first-subdir.file-b
    second-subdir.file-c
and (for example) first function will be
    (defn first-subdir.file-a
      ([file-a data] ...)
      ([file-a] (recur file-a file-a)))
