package com.azandria.data.places;

import com.azandria.datadude.utils.DataRequestBuilder;
import com.azandria.datadude.utils.IDataRequestFilter;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.Collection;
import java.util.Timer;
import java.util.TimerTask;

public class PlaceStubbedApiRequestMethod extends PlaceApiRequestMethod {

    public PlaceStubbedApiRequestMethod(int id) {
        super(id);
    }

    @Override
    public void doRequest(final DataRequestBuilder.RequestMethodListener<Place> listener, Collection<IDataRequestFilter> filters) {
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                /* RAW

                 [
                   {
                      "id":1,
                      "name":"Boston, United States",
                      "wiki_content":"Boston is the capital and largest city of the Commonwealth of Massachusetts in the United States. Boston also served as the historic county seat of Suffolk County until Massachusetts disbanded county government in 1999. The city proper covers 48 square miles with an estimated population of 655,884 in 2014, making it the largest city in New England and the 24th largest city in the United States.",
                      "tripadvisor_content":"Walk the Freedom Trail the first time you visit Boston, and you'll quickly get a sense of this coastal city's revolutionary spirit and history. But make sure you also explore some of Boston's fine museums (try the Gardner, featuring art masterpieces displayed in their collector's mansion) and old neighborhoods (like the North End, Boston's Little Italy). You can't claim to have experienced real Boston culture, though, unless you've watched a Red Sox baseball game from the bleachers.",
                      "map_uri":"geo:42.3583855,-71.0621501?z=14.32",
                      "image_url":"http://res.cloudinary.com/azandriacloud/image/upload/v1445236445/eu1724f327zef5txhugj.jpg",
                      "wikipedia_url":"https://en.wikipedia.org/wiki/Boston",
                      "tripadvisor_url":"http://www.tripadvisor.com/Tourism-g60745-Boston_Massachusetts-Vacations.html"
                   },
                   {
                      "id":2,
                      "name":"New Hampshire, United States",
                      "wiki_content":"New Hampshire is a state in the New England region of the northeastern United States. It is bordered by Massachusetts to the south, Vermont to the west, Maine and the Atlantic Ocean to the east, and the Canadian province of Quebec to the north. New Hampshire is the 5th smallest by land area, and the 9th least populous of the 50 United States.",
                      "tripadvisor_content":"",
                      "map_uri":"geo:43.829318,-72.8183337?z=7.8",
                      "image_url":"http://res.cloudinary.com/azandriacloud/image/upload/v1445236450/ht0zusmffzpav1lgkt1r.jpg",
                      "wikipedia_url":"https://en.wikipedia.org/wiki/New_Hampshire",
                      "tripadvisor_url":"http://www.tripadvisor.com/Tourism-g28950-New_Hampshire-Vacations.html"
                   },
                   {
                      "id":3,
                      "name":"Tromsø, Norway",
                      "wiki_content":"Tromsø is a city and municipality in Troms county, Norway. The administrative centre of the municipality is the city of Tromsø. Outside of Norway, Tromso and Tromsö are alternative spellings of the city. Tromsø is considered the northernmost city in the world with a population above 50,000. The most populous city north of it is Alta, Norway, with a population of 14,272 (2013).",
                      "tripadvisor_content":"The fjords and mountain ranges of Tromso are simply magical. Here, the northern lights sparkle across the same navy blue sky that's illuminated by the midnight sun. You'll be spellbound by Tromso's enchanting fishing villages, fragrant botanical gardens and crystalline waterfalls. Music is a major part of the Tromso culture, particularly techno and electronic music, which adds some thumping thunder to the city once a year during the annual Insomnia Festival.",
                      "map_uri":"geo:69.6736408,18.8832413?z=12",
                      "image_url":"http://res.cloudinary.com/azandriacloud/image/upload/v1445236468/i7jw9bkzggxabnx81qku.jpg",
                      "wikipedia_url":"https://en.wikipedia.org/wiki/Troms%C3%B8",
                      "tripadvisor_url":"http://www.tripadvisor.com/Tourism-g190475-Tromso_Troms_Northern_Norway-Vacations.html"
                   },
                   {
                      "id":4,
                      "name":"West Palm Beach, United States",
                      "wiki_content":"West Palm Beach is a city in and the county seat of Palm Beach County, Florida, United States.[4] It is one of the three main cities in South Florida. The population was 99,919 at the 2010 census. The University of Florida Bureau of Economic and Business Research (BEBR) estimates a 2014 population of 104,031, a 4.1% increase from 2010. It is the oldest municipality in the South Florida metropolitan area, having been incorporated as a city two years before Miami in November 1894. West Palm Beach is located approximately 68 miles (109 km) north of Downtown Miami.",
                      "tripadvisor_content":"With sunny skies, nearly perfect year-round weather and a range of outdoor and cultural attractions, West Palm Beach presents the ideal setting for a weekend escape. Enjoy a game of golf at a number of pristine local courses or hit the beach for a day of tanning and catching up on your reading. See African chimps and endangered species like the Southern White rhinoceros wander free in a natural habitat at the Lion Country Safari. You could spend an entire day on Peanut Island, a popular tourist attraction that houses a bunker built for President John F. Kennedy during the Cold War...",
                      "map_uri":"geo:26.7389888,-80.2728633?z=10.32",
                      "image_url":"http://res.cloudinary.com/azandriacloud/image/upload/v1445236451/kgyabtg0ca9f89dpp1wp.jpg",
                      "wikipedia_url":"https://en.wikipedia.org/wiki/West_Palm_Beach,_Florida",
                      "tripadvisor_url":"http://www.tripadvisor.com/Tourism-g34731-West_Palm_Beach_Florida-Vacations.html"
                   },
                   {
                      "id":5,
                      "name":"New York City, United States",
                      "wiki_content":"New York—often called New York City or the City of New York to distinguish it from the State of New York, of which it is a part—is the most populous city in the United States[1] and the center of the New York metropolitan area, the premier gateway for legal immigration to the United States and one of the most populous urban agglomerations in the world. A global power city, New York exerts a significant impact upon commerce, finance, media, art, fashion, research, technology, education, and entertainment, its fast pace defining the term New York minute. Home to the headquarters of the United Nations,[17] New York is an important center for international diplomacy and has been described as the cultural and financial capital of the world.",
                      "tripadvisor_content":"Conquering New York in one visit is impossible. Instead, hit the must-sees – the Empire State Building, the Statue of Liberty, Central Park, the Metropolitan Museum of Art – and then explore off the beaten path with visits to The Cloisters or one of the city’s libraries. Indulge in the bohemian shops of the West Village or the fine dining of the Upper West Side. The bustling marketplace inside of Grand Central Station gives you a literal taste of the best the city has to offer.",
                      "map_uri":"geo:40.7053094,-74.2588822,?z=10",
                      "image_url":"http://res.cloudinary.com/azandriacloud/image/upload/v1445236459/a7h2zy0ftnb08hyk8mil.jpg",
                      "wikipedia_url":"https://en.wikipedia.org/wiki/New_York_City",
                      "tripadvisor_url":"http://www.tripadvisor.com/Tourism-g60763-New_York_City_New_York-Vacations.html"
                   },
                   {
                      "id":6,
                      "name":"Galway, Ireland",
                      "wiki_content":"Galway is a city in the West of Ireland in the province of Connacht. Galway City Council is the local authority for the city. Galway lies on the River Corrib between Lough Corrib and Galway Bay and is surrounded by County Galway. It is the fourth most populous urban area in the Republic of Ireland and the sixth most populous city in the island of Ireland.",
                      "tripadvisor_content":"For visitors, the best thing about Galway is that you can walk everywhere. As soon as you arrive, enjoy a walk through the city streets. There, you'll find lively pubs, independent shops and winding cobblestone streets packed with students, artists, writers and craftspeople. You may even hear Gaelic spoken. For a day trip, take a ferry to the island of Inis Mor. You'll return refreshed by the ocean air and Inis Mor's breathtaking scenery. Oh, and be sure to wear sunscreen on the island, no matter how chilly it is. (Just trust us, okay?)",
                      "map_uri":"geo:53.2714336,-9.067119?z=14.32",
                      "image_url":"http://res.cloudinary.com/azandriacloud/image/upload/v1445236454/weln95pnbc97hblzxii6.jpg",
                      "wikipedia_url":"https://en.wikipedia.org/wiki/Galway",
                      "tripadvisor_url":"http://www.tripadvisor.com/Tourism-g186609-Galway_County_Galway_Western_Ireland-Vacations.html"
                   }
                ]
                 */
                String response = "[\n" +
                        "   {\n" +
                        "      \"id\":1,\n" +
                        "      \"name\":\"Boston, United States\",\n" +
                        "      \"wiki_content\":\"Boston is the capital and largest city of the Commonwealth of Massachusetts in the United States. Boston also served as the historic county seat of Suffolk County until Massachusetts disbanded county government in 1999. The city proper covers 48 square miles with an estimated population of 655,884 in 2014, making it the largest city in New England and the 24th largest city in the United States.\",\n" +
                        "      \"tripadvisor_content\":\"Walk the Freedom Trail the first time you visit Boston, and you'll quickly get a sense of this coastal city's revolutionary spirit and history. But make sure you also explore some of Boston's fine museums (try the Gardner, featuring art masterpieces displayed in their collector's mansion) and old neighborhoods (like the North End, Boston's Little Italy). You can't claim to have experienced real Boston culture, though, unless you've watched a Red Sox baseball game from the bleachers.\",\n" +
                        "      \"map_uri\":\"geo:42.3583855,-71.0621501?z=14.32\",\n" +
                        "      \"image_url\":\"http://res.cloudinary.com/azandriacloud/image/upload/v1445236445/eu1724f327zef5txhugj.jpg\",\n" +
                        "      \"wikipedia_url\":\"https://en.wikipedia.org/wiki/Boston\",\n" +
                        "      \"tripadvisor_url\":\"http://www.tripadvisor.com/Tourism-g60745-Boston_Massachusetts-Vacations.html\"\n" +
                        "   },\n" +
                        "   {\n" +
                        "      \"id\":2,\n" +
                        "      \"name\":\"New Hampshire, United States\",\n" +
                        "      \"wiki_content\":\"New Hampshire is a state in the New England region of the northeastern United States. It is bordered by Massachusetts to the south, Vermont to the west, Maine and the Atlantic Ocean to the east, and the Canadian province of Quebec to the north. New Hampshire is the 5th smallest by land area, and the 9th least populous of the 50 United States.\",\n" +
                        "      \"tripadvisor_content\":\"\",\n" +
                        "      \"map_uri\":\"geo:43.829318,-72.8183337?z=7.8\",\n" +
                        "      \"image_url\":\"http://res.cloudinary.com/azandriacloud/image/upload/v1445236450/ht0zusmffzpav1lgkt1r.jpg\",\n" +
                        "      \"wikipedia_url\":\"https://en.wikipedia.org/wiki/New_Hampshire\",\n" +
                        "      \"tripadvisor_url\":\"http://www.tripadvisor.com/Tourism-g28950-New_Hampshire-Vacations.html\"\n" +
                        "   },\n" +
                        "   {\n" +
                        "      \"id\":3,\n" +
                        "      \"name\":\"Tromsø, Norway\",\n" +
                        "      \"wiki_content\":\"Tromsø is a city and municipality in Troms county, Norway. The administrative centre of the municipality is the city of Tromsø. Outside of Norway, Tromso and Tromsö are alternative spellings of the city. Tromsø is considered the northernmost city in the world with a population above 50,000. The most populous city north of it is Alta, Norway, with a population of 14,272 (2013).\",\n" +
                        "      \"tripadvisor_content\":\"The fjords and mountain ranges of Tromso are simply magical. Here, the northern lights sparkle across the same navy blue sky that's illuminated by the midnight sun. You'll be spellbound by Tromso's enchanting fishing villages, fragrant botanical gardens and crystalline waterfalls. Music is a major part of the Tromso culture, particularly techno and electronic music, which adds some thumping thunder to the city once a year during the annual Insomnia Festival.\",\n" +
                        "      \"map_uri\":\"geo:69.6736408,18.8832413?z=12\",\n" +
                        "      \"image_url\":\"http://res.cloudinary.com/azandriacloud/image/upload/v1445236468/i7jw9bkzggxabnx81qku.jpg\",\n" +
                        "      \"wikipedia_url\":\"https://en.wikipedia.org/wiki/Troms%C3%B8\",\n" +
                        "      \"tripadvisor_url\":\"http://www.tripadvisor.com/Tourism-g190475-Tromso_Troms_Northern_Norway-Vacations.html\"\n" +
                        "   },\n" +
                        "   {\n" +
                        "      \"id\":4,\n" +
                        "      \"name\":\"West Palm Beach, United States\",\n" +
                        "      \"wiki_content\":\"West Palm Beach is a city in and the county seat of Palm Beach County, Florida, United States.[4] It is one of the three main cities in South Florida. The population was 99,919 at the 2010 census. The University of Florida Bureau of Economic and Business Research (BEBR) estimates a 2014 population of 104,031, a 4.1% increase from 2010. It is the oldest municipality in the South Florida metropolitan area, having been incorporated as a city two years before Miami in November 1894. West Palm Beach is located approximately 68 miles (109 km) north of Downtown Miami.\",\n" +
                        "      \"tripadvisor_content\":\"With sunny skies, nearly perfect year-round weather and a range of outdoor and cultural attractions, West Palm Beach presents the ideal setting for a weekend escape. Enjoy a game of golf at a number of pristine local courses or hit the beach for a day of tanning and catching up on your reading. See African chimps and endangered species like the Southern White rhinoceros wander free in a natural habitat at the Lion Country Safari. You could spend an entire day on Peanut Island, a popular tourist attraction that houses a bunker built for President John F. Kennedy during the Cold War...\",\n" +
                        "      \"map_uri\":\"geo:26.7389888,-80.2728633?z=10.32\",\n" +
                        "      \"image_url\":\"http://res.cloudinary.com/azandriacloud/image/upload/v1445236451/kgyabtg0ca9f89dpp1wp.jpg\",\n" +
                        "      \"wikipedia_url\":\"https://en.wikipedia.org/wiki/West_Palm_Beach,_Florida\",\n" +
                        "      \"tripadvisor_url\":\"http://www.tripadvisor.com/Tourism-g34731-West_Palm_Beach_Florida-Vacations.html\"\n" +
                        "   },\n" +
                        "   {\n" +
                        "      \"id\":5,\n" +
                        "      \"name\":\"New York City, United States\",\n" +
                        "      \"wiki_content\":\"New York—often called New York City or the City of New York to distinguish it from the State of New York, of which it is a part—is the most populous city in the United States[1] and the center of the New York metropolitan area, the premier gateway for legal immigration to the United States and one of the most populous urban agglomerations in the world. A global power city, New York exerts a significant impact upon commerce, finance, media, art, fashion, research, technology, education, and entertainment, its fast pace defining the term New York minute. Home to the headquarters of the United Nations,[17] New York is an important center for international diplomacy and has been described as the cultural and financial capital of the world.\",\n" +
                        "      \"tripadvisor_content\":\"Conquering New York in one visit is impossible. Instead, hit the must-sees – the Empire State Building, the Statue of Liberty, Central Park, the Metropolitan Museum of Art – and then explore off the beaten path with visits to The Cloisters or one of the city’s libraries. Indulge in the bohemian shops of the West Village or the fine dining of the Upper West Side. The bustling marketplace inside of Grand Central Station gives you a literal taste of the best the city has to offer.\",\n" +
                        "      \"map_uri\":\"geo:40.7053094,-74.2588822,?z=10\",\n" +
                        "      \"image_url\":\"http://res.cloudinary.com/azandriacloud/image/upload/v1445236459/a7h2zy0ftnb08hyk8mil.jpg\",\n" +
                        "      \"wikipedia_url\":\"https://en.wikipedia.org/wiki/New_York_City\",\n" +
                        "      \"tripadvisor_url\":\"http://www.tripadvisor.com/Tourism-g60763-New_York_City_New_York-Vacations.html\"\n" +
                        "   },\n" +
                        "   {\n" +
                        "      \"id\":6,\n" +
                        "      \"name\":\"Galway, Ireland\",\n" +
                        "      \"wiki_content\":\"Galway is a city in the West of Ireland in the province of Connacht. Galway City Council is the local authority for the city. Galway lies on the River Corrib between Lough Corrib and Galway Bay and is surrounded by County Galway. It is the fourth most populous urban area in the Republic of Ireland and the sixth most populous city in the island of Ireland.\",\n" +
                        "      \"tripadvisor_content\":\"For visitors, the best thing about Galway is that you can walk everywhere. As soon as you arrive, enjoy a walk through the city streets. There, you'll find lively pubs, independent shops and winding cobblestone streets packed with students, artists, writers and craftspeople. You may even hear Gaelic spoken. For a day trip, take a ferry to the island of Inis Mor. You'll return refreshed by the ocean air and Inis Mor's breathtaking scenery. Oh, and be sure to wear sunscreen on the island, no matter how chilly it is. (Just trust us, okay?)\",\n" +
                        "      \"map_uri\":\"geo:53.2714336,-9.067119?z=14.32\",\n" +
                        "      \"image_url\":\"http://res.cloudinary.com/azandriacloud/image/upload/v1445236454/weln95pnbc97hblzxii6.jpg\",\n" +
                        "      \"wikipedia_url\":\"https://en.wikipedia.org/wiki/Galway\",\n" +
                        "      \"tripadvisor_url\":\"http://www.tripadvisor.com/Tourism-g186609-Galway_County_Galway_Western_Ireland-Vacations.html\"\n" +
                        "   }\n" +
                        "]";
                JSONArray placeArray = null;

                try {
                    placeArray = new JSONArray(response);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                listener.onCompleted(Place.from(placeArray, mId));
            }
        };
        Timer timer = new Timer();
        timer.schedule(task, 1000);
    }
}
