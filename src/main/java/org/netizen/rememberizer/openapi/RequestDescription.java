package org.netizen.rememberizer.openapi;

public class RequestDescription {

    private RequestDescription() {
        throw new AssertionError("Class is not instantiable");
    }

    public static final String GET_NOTE_BY_ID =
            """
            {
              "id": 1,
              "subject": "My subject",
              "body": "My body"
            }
            """;

    public static final String CREATE_NOTE =
            """
            {
              "id": null,
              "subject": "My subject",
              "body": "My body"
            }
            """;

    public static final String UPDATE_NOTE =
            """
            {
              "id": 1,
              "subject": "Edited subject",
              "body": "Edited body"
            }
            """;

    public static final String GET_PAGINATED_NOTES =
            """
            {
              "content": [
                {
                  "id": 6,
                  "subject": "Subject",
                  "body": "Body"
                }
              ],
              "pageable": {
                "pageNumber": 0,
                "pageSize": 1,
                "sort": {
                  "sorted": true,
                  "empty": false,
                  "unsorted": false
                },
                "offset": 0,
                "paged": true,
                "unpaged": false
              },
              "last": false,
              "totalPages": 4,
              "totalElements": 4,
              "size": 1,
              "number": 0,
              "sort": {
                "sorted": true,
                "empty": false,
                "unsorted": false
              },
              "first": true,
              "numberOfElements": 1,
              "empty": false
            }
            """;

}
