![http-retry](https://media.giphy.com/media/3o6MbaH9LYlaVpHWBa/giphy.gif)

http(s) is great, but a not so well kept secret of it is that requests may fail for different reasons. In many cases they're out of the control of the client and a simple retry can fix it. This is what this library is doing, and nothing else. Written for [clj-http]()

## Usage

Given this request:

```clojure
(ns my-app.core
  (:require [clj-http.client :as client]))

(client/get "http://example.com"
            {:headers {:foo ["bar" "baz"], :eggplant "quux"}})
```

If you want to retry it 3 times, change it to this:

```clojure
(ns my-app.core
  (:require [clj-http.client :as client]
            [billfront.http-retry :refer [with-retries]]))

(with-retries 3
              client/get
              "http://example.com"
              {:headers {:foo ["bar" "baz"], :eggplant "quux"}})
```

## Deploy a new version

1. Update the version in the [`pom.xml`](pom.xml)
2. Commit this change: `git commit -p`
3. Tag the release and push it and the rest: ``git tag v`grep -oP '(?<=^  <version>).*?(?=</version>)' pom.xml` && git push --tags && git push``
4. Run `clojure -M:jar`
5. Run ` CLOJARS_USERNAME=billfront CLOJARS_PASSWORD=<clojars token> clojure -M:deploy`. Get a token from [Clojars dashboard](https://clojars.org/tokens)

## License

Copyright 2020 BillFront GmbH

Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
