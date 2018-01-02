# populate-activities
Pulling 119 records of activity data within Italy and uploading to Recombee database.

Retrieving from Activity Search v2 API: 
- Category = event
- start_date = "2018-01-01.."
- country = Italy

Saving to "activity.csv" and after manually correcting some data errors populating Recombee items table with the following properties:
- id (string)
- type (string)
- name (string)
- city (string)
- topic (string)
- from (string)
- to (string)
