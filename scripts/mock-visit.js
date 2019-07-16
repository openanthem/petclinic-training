db.visit.drop();
db.visit.insert({
    "_id" : NumberLong(1),
    "petId" : "1",
    "appointment" : ISODate("2019-06-18T00:00:00.000Z"),
    "ownerName" : "Mickey Mouse",
    "petName" : "Pluto",
    "reasonForVisit" : "Sick",
    "visitOutcome" : "Recommended rest and extra fluids until fully recovered.",
    "visitNote" : "N/A",
    "status" : "COMPLETE",
    "_class" : "com.atlas.client.extension.petclinic.core.owner.Visit",
    "createdDate" : {
        "_date" : ISODate("2019-06-28T14:13:50.756Z"),
        "_zone" : "America/Chicago"
    },
    "lastModifiedDate" : {
        "_date" : ISODate("2019-06-28T14:14:03.396Z"),
        "_zone" : "America/Chicago"
    },
    "version" : NumberLong(0)
});
