# csv-to-sql

Weekend project to play around with [clojure.tools.cli](https://github.com/clojure/tools.cli) and a bit with [honey.sql](https://github.com/seancorfield/honeysql) to create a small command line app.

Given a CSV file, will ouput an SQL file that is able to be used to import the data into a database

## Building

With lein, run `lein uberjar`

## Usage

```bash
$ java -jar csv-to-sql-0.1.0-standalone.jar [args]
```

## Options

```
  -i, --input INPUT                  Input CSV file path
  -o, --output OUTPUT    output.sql  Output SQL file path
  -t, --table TABLE                  Name of table to insert to
      --headers HEADER   []          CSV header
      --skip-first                   Skip the first row
  -d, --dialect DIALECT  :ansi       SQL dialect
  -h, --help
```

## Examples

```bash
$ java -jar csv-to-sql-0.1.0-standalone.jar --input weather.csv --table weather
```