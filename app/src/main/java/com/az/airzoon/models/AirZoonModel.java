package com.az.airzoon.models;

import com.az.airzoon.R;
import com.az.airzoon.constants.Constants;
import com.az.airzoon.database.AirZoonDB;
import com.az.airzoon.dataobjects.AirZoonDo;
import com.google.gson.Gson;

import org.json.JSONArray;

import java.util.ArrayList;

/**
 * Created by siddharth on 7/26/2016.
 */
public class AirZoonModel {

    private ArrayList<AirZoonDo> airZoonDoArrayList = new ArrayList<>();
    private ArrayList<AirZoonDo> filteredList = new ArrayList<>();
    public static AirZoonModel airZoonModel = null;

    public static AirZoonModel getInstance() {
        if (airZoonModel == null) {
            airZoonModel = new AirZoonModel();
        }
        return airZoonModel;
    }

    public void loadAndParseHotSpot(String hotspotString, AirZoonDB airZoonDB) {
        try {
            airZoonDoArrayList.clear();
            filteredList.clear();

            boolean foundInDBForFirstTimeLaunch = false;

            JSONArray itemArray = null;

            //this will be null if this method is called initially
            if (hotspotString == null) {
                if (airZoonDB.getTableCount() > 0) {
                    foundInDBForFirstTimeLaunch = true;
                    airZoonDoArrayList = airZoonDB.getAllHotSpotList();
                } else {
                    itemArray = new JSONArray(getStaticString());
                }
            } else {
                itemArray = new JSONArray(hotspotString);
            }


            //if not found in db in launch
            if (!foundInDBForFirstTimeLaunch) {
                ArrayList<AirZoonDo> airZoonNewDoArrayList = new ArrayList<>();
                Gson gson = new Gson();

                //all new added into the list
                for (int i = 0; i < itemArray.length(); i++) {
                    AirZoonDo airZoonDo = gson.fromJson(itemArray.get(i).toString(), AirZoonDo.class);
                    airZoonNewDoArrayList.add(airZoonDo);
                    airZoonDB.addOrUpdateAirZoonHotSpot(airZoonDo);
                }

                //delete the one which doesn't not exist in remaining
                airZoonDoArrayList = airZoonDB.getAllHotSpotList();


                ArrayList<AirZoonDo> spotsWhichAreNotInNewList = new ArrayList<>();
                //compare if any extra spot present in static list
                for (int i = 0; i < airZoonDoArrayList.size(); i++) {
                    AirZoonDo airZoonDoNeedToCheck = airZoonDoArrayList.get(i);

                    boolean isFound = false;
                    for (int j = 0; j < airZoonNewDoArrayList.size(); j++) {
                        if (airZoonDoNeedToCheck.getId().equals(airZoonNewDoArrayList.get(j).getId())) {
//                            System.out.println(">>sid found >> true");
                            isFound = true;
                            break;
                        }
                    }

                    //add in index if not found in new list
                    if (!isFound) {
                        spotsWhichAreNotInNewList.add(airZoonDoNeedToCheck);
                    }
                }

//                System.out.println(">>sid total spot not found in new list >> " + spotsWhichAreNotInNewList.size());

                //delete which are not found in latest list
                for (int i = 0; i < spotsWhichAreNotInNewList.size(); i++) {
                    AirZoonDo airZoonDoNeedToCheck = spotsWhichAreNotInNewList.get(i);
//                    System.out.println(">>sid deleted >> " + airZoonDoNeedToCheck.getName() + " id>>" + airZoonDoNeedToCheck.getId() + " >>size >>" + i);
                    airZoonDB.deleteASpot(airZoonDoNeedToCheck.getId());
                    airZoonDoArrayList.remove(airZoonDoNeedToCheck);
                }

            }


//            System.out.println(">>sid size >> " + airZoonDoArrayList.size());
            //filtering the list
            for (int i = 0; i < airZoonDoArrayList.size(); i++) {
                canBeAddedInFilteredList(airZoonDoArrayList.get(i));
            }


        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    private void canBeAddedInFilteredList(AirZoonDo airZoonDo) {
        if (FilterSettingModel.getInstance().getFilterSetting(airZoonDo.getCategory())
                && FilterSettingModel.getInstance().getFilterSetting(airZoonDo.getType())) {
            filteredList.add(airZoonDo);
        }
    }

    public ArrayList<AirZoonDo> getHotSpotListByType(String type) {
        ArrayList<AirZoonDo> newList = new ArrayList<>();
        for (int i = 0; i < airZoonDoArrayList.size(); i++) {
            if (airZoonDoArrayList.get(i).getType().equalsIgnoreCase(type)) {
                newList.add(airZoonDoArrayList.get(i));
            }
        }
//        System.out.println(">>Filtered List size>>" + newList.size());
        return newList;
    }

    public ArrayList<AirZoonDo> getFaviorateHotSpotList() {
        ArrayList<AirZoonDo> newList = new ArrayList<>();
        for (int i = 0; i < airZoonDoArrayList.size(); i++) {
            if (airZoonDoArrayList.get(i).isFaviourate()) {
                newList.add(airZoonDoArrayList.get(i));
            }
        }
        return newList;
    }

    public void removeFavirates() {
        for (int i = 0; i < airZoonDoArrayList.size(); i++) {
            airZoonDoArrayList.get(i).setFaviourate(false);
        }
    }

    public ArrayList<AirZoonDo> getHotSpotListByCategory(String cateogry) {
        ArrayList<AirZoonDo> newList = new ArrayList<>();
        for (int i = 0; i < airZoonDoArrayList.size(); i++) {
            if (airZoonDoArrayList.get(i).getCategory().equalsIgnoreCase(cateogry)) {
                newList.add(airZoonDoArrayList.get(i));
            }
        }
//        System.out.println(">>Filtered List size>>" + newList.size());
        return newList;
    }

    public ArrayList<AirZoonDo> getHotSpotListBySearchTextOnly(String filterText) {
        ArrayList<AirZoonDo> newList = new ArrayList<>();
        for (int i = 0; i < airZoonDoArrayList.size(); i++) {
            if (containsIgnoreCase(airZoonDoArrayList.get(i).getName(), filterText)) {
                newList.add(airZoonDoArrayList.get(i));
            }
//            System.out.println(">>Filtered List size>>" + newList.size());
        }
        return newList;
    }

    public void setFav(String id) {
        for (int i = 0; i < airZoonDoArrayList.size(); i++) {
            if (airZoonDoArrayList.get(i).getId().equalsIgnoreCase(id)) {
                airZoonDoArrayList.get(i).setFaviourate(true);
                break;
            }
        }
    }

    public ArrayList<AirZoonDo> getHotSpotListBySearchTextAndType(String filterText, String type) {
        ArrayList<AirZoonDo> newList = new ArrayList<>();
        for (int i = 0; i < airZoonDoArrayList.size(); i++) {
            if (containsIgnoreCase(airZoonDoArrayList.get(i).getName(), filterText)
                    && airZoonDoArrayList.get(i).getType().equalsIgnoreCase(type)) {
                newList.add(airZoonDoArrayList.get(i));
            }
//            System.out.println(">>Filtered List size>>" + newList.size());
        }


        return newList;
    }

    public boolean containsIgnoreCase(String src, String what) {
        final int length = what.length();
        if (length == 0)
            return true; // Empty string is contained

        final char firstLo = Character.toLowerCase(what.charAt(0));
        final char firstUp = Character.toUpperCase(what.charAt(0));

        for (int i = src.length() - length; i >= 0; i--) {
            // Quick check before calling the more expensive regionMatches() method:
            final char ch = src.charAt(i);
            if (ch != firstLo && ch != firstUp)
                continue;

            if (src.regionMatches(true, i, what, 0, length))
                return true;
        }

        return false;
    }


    public int getHotSpotBigImageResByCat(String cateogry) {
        if (cateogry.equalsIgnoreCase(Constants.HOTSPOT_TYPE_AIRZOON)) {
            return R.drawable.marker_airzoon;
        } else if (cateogry.equalsIgnoreCase(Constants.HOTSPOT_TYPE_PAID)) {
            return R.drawable.marker_paid;
        } else if (cateogry.equalsIgnoreCase(Constants.HOTSPOT_TYPE_FREE)) {
            return R.drawable.marker_free;
        } else if (cateogry.equalsIgnoreCase(Constants.HOTSPOT_CATEGORY_RESTAURANT)) {
            return R.drawable.resturant_black;
        } else if (cateogry.equalsIgnoreCase(Constants.HOTSPOT_CATEGORY_HOTEL)) {
            return R.drawable.hotel_black;
        } else if (cateogry.equalsIgnoreCase(Constants.HOTSPOT_CATEGORY_SPORT_CENTER)) {
            return R.drawable.fitness_black;
        } else if (cateogry.equalsIgnoreCase(Constants.HOTSPOT_CATEGORY_BAR)) {
            return R.drawable.bar1_black;
        } else if (cateogry.equalsIgnoreCase(Constants.HOTSPOT_CATEGORY_FAST_FOOD)) {
            return R.drawable.fast_food_black;
        } else if (cateogry.equalsIgnoreCase(Constants.HOTSPOT_CATEGORY_MALL)) {
            return R.drawable.mall_black;
        } else if (cateogry.equalsIgnoreCase(Constants.HOTSPOT_CATEGORY_AIRPORT)) {
            return R.drawable.airport_black;
        } else if (cateogry.equalsIgnoreCase(Constants.HOTSPOT_CATEGORY_BARBER)) {
            return R.drawable.barber_black;
        } else if (cateogry.equalsIgnoreCase(Constants.HOTSPOT_CATEGORY_PANCAKES_WAFFLES)) {
            return R.drawable.pancake_waffels_black;
        } else if (cateogry.equalsIgnoreCase(Constants.HOTSPOT_CATEGORY_HEALTH_AND_WELLNESS)) {
            return R.drawable.health_black;
        } else {
            //others
            return R.drawable.others_cat_black;
        }

    }


    public int getSpeeddoMeterImage(String speedText) {
        if (speedText.contains("1-5")) {
            return R.drawable.speedone;
        } else if (speedText.contains("5-10")) {
            return R.drawable.speedtwo;
        } else if (speedText.contains("10-15")) {
            return R.drawable.speedthree;
        } else if (speedText.contains("15-20")) {
            return R.drawable.speedfour;
        } else {
            return R.drawable.speedone;
        }

    }


    public int getHotSpotTypeCatLanguageProtedText(String cateogry) {
        if (cateogry.equalsIgnoreCase(Constants.HOTSPOT_TYPE_AIRZOON)) {
            return R.string.airZoonText;
        } else if (cateogry.equalsIgnoreCase(Constants.HOTSPOT_TYPE_PAID)) {
            return R.string.paidText;
        } else if (cateogry.equalsIgnoreCase(Constants.HOTSPOT_TYPE_FREE)) {
            return R.string.freeText;
        } else if (cateogry.equalsIgnoreCase(Constants.HOTSPOT_CATEGORY_RESTAURANT)) {
            return R.string.catRestaurant;
        } else if (cateogry.equalsIgnoreCase(Constants.HOTSPOT_CATEGORY_HOTEL)) {
            return R.string.catHotel;
        } else if (cateogry.equalsIgnoreCase(Constants.HOTSPOT_CATEGORY_SPORT_CENTER)) {
            return R.string.catSportCenter;
        } else if (cateogry.equalsIgnoreCase(Constants.HOTSPOT_CATEGORY_BAR)) {
            return R.string.catBar;
        } else if (cateogry.equalsIgnoreCase(Constants.HOTSPOT_CATEGORY_FAST_FOOD)) {
            return R.string.catFastFood;
        } else if (cateogry.equalsIgnoreCase(Constants.HOTSPOT_CATEGORY_MALL)) {
            return R.string.catMall;
        } else if (cateogry.equalsIgnoreCase(Constants.HOTSPOT_CATEGORY_AIRPORT)) {
            return R.string.catAirport;
        } else if (cateogry.equalsIgnoreCase(Constants.HOTSPOT_CATEGORY_BARBER)) {
            return R.string.catBarber;
        } else if (cateogry.equalsIgnoreCase(Constants.HOTSPOT_CATEGORY_PANCAKES_WAFFLES)) {
            return R.string.catPancakesWaffles;
        } else if (cateogry.equalsIgnoreCase(Constants.HOTSPOT_CATEGORY_HEALTH_AND_WELLNESS)) {
            return R.string.catHealthAndWellness;
        } else {
            //others
            return R.string.catOther;
        }

    }

    public int getHotSpotSmallImageResByCat(String cateogry) {
        if (cateogry.equalsIgnoreCase(Constants.HOTSPOT_TYPE_AIRZOON)) {
            return R.drawable.marker_airzoon;
        } else if (cateogry.equalsIgnoreCase(Constants.HOTSPOT_TYPE_PAID)) {
            return R.drawable.marker_paid;
        } else if (cateogry.equalsIgnoreCase(Constants.HOTSPOT_TYPE_FREE)) {
            return R.drawable.marker_free;
        } else if (cateogry.equalsIgnoreCase(Constants.HOTSPOT_CATEGORY_RESTAURANT)) {
            return R.drawable.restset;
        } else if (cateogry.equalsIgnoreCase(Constants.HOTSPOT_CATEGORY_HOTEL)) {
            return R.drawable.hotel;
        } else if (cateogry.equalsIgnoreCase(Constants.HOTSPOT_CATEGORY_SPORT_CENTER)) {
            return R.drawable.fitnessset;
        } else if (cateogry.equalsIgnoreCase(Constants.HOTSPOT_CATEGORY_BAR)) {
            return R.drawable.barset;
        } else if (cateogry.equalsIgnoreCase(Constants.HOTSPOT_CATEGORY_FAST_FOOD)) {
            return R.drawable.fastfoodset;
        } else if (cateogry.equalsIgnoreCase(Constants.HOTSPOT_CATEGORY_MALL)) {
            return R.drawable.servicestoreset;
        } else if (cateogry.equalsIgnoreCase(Constants.HOTSPOT_CATEGORY_AIRPORT)) {
            return R.drawable.airset;
        } else if (cateogry.equalsIgnoreCase(Constants.HOTSPOT_CATEGORY_BARBER)) {
            return R.drawable.barberset;
        } else if (cateogry.equalsIgnoreCase(Constants.HOTSPOT_CATEGORY_PANCAKES_WAFFLES)) {
            return R.drawable.pancakewaffles;
        } else if (cateogry.equalsIgnoreCase(Constants.HOTSPOT_CATEGORY_HEALTH_AND_WELLNESS)) {
            return R.drawable.healthset;
        } else {
            //others
            return R.drawable.others;
        }

    }


    public int getHotSpotMarkerResByType(String type) {
        if (type.equalsIgnoreCase(Constants.HOTSPOT_TYPE_AIRZOON)) {
            return R.drawable.marker_airzoon;
        } else if (type.equalsIgnoreCase(Constants.HOTSPOT_TYPE_PAID)) {
            return R.drawable.marker_paid;
        } else {
            return R.drawable.marker_free;
        }
    }

    public int getBigHotSpotMarkerResByType(String type) {
        if (type.equalsIgnoreCase(Constants.HOTSPOT_TYPE_AIRZOON)) {
            return R.drawable.marker_airzoon_big;
        } else if (type.equalsIgnoreCase(Constants.HOTSPOT_TYPE_PAID)) {
            return R.drawable.marker_paid_big;
        } else {
            return R.drawable.marker_free_big;
        }
    }

    public ArrayList<AirZoonDo> getAirZoonDoArrayList() {
        return airZoonDoArrayList;
    }

    public void setAirZoonDoArrayList(ArrayList<AirZoonDo> airZoonDoArrayList) {
        this.airZoonDoArrayList = airZoonDoArrayList;
    }

    public ArrayList<AirZoonDo> getFilteredList() {
        return filteredList;
    }


    private String getStaticString() {

        return "[{\"id\":\"1\",\"name\":\"Hippopotms\",\"date\":\"\",\"address\":\"Centre commercial la Galleria\",\"address2\":\"\",\"zip\":\"97232\",\"country\":\"Martinique\",\"city\":\"Lamentin\",\"lat\":\"14.6197778\",\"lng\":\"-61.0203611\",\"image\":\"image\\/1\\/test.png\",\"phone\":\"0596 54 62 66\",\"type\":\"paid\",\"category\":\"Sport Center\",\"is_free\":\"\",\"speed\":\"1--5\",\"fav_count\":\"0\",\"category_image\":\"category\\/fitness.png\",\"opening_one\":\"--\",\"opening_two\":\"--\"},{\"id\":\"2\",\"name\":\"Ti Grill\",\"date\":\"\",\"address\":\"Parking centre commercial annette \",\"address2\":\"Carrefour Marin\",\"zip\":\"97290\",\"country\":\"Martinique\",\"city\":\"Marin\",\"lat\":\"14.4720833\",\"lng\":\"-60.877694399999996\",\"image\":\"image\\/other.jpg\",\"phone\":\"0596 74 84 86\",\"type\":\"free\",\"category\":\"Restaurant\",\"is_free\":\"\",\"speed\":\"1--5\",\"fav_count\":\"0\",\"category_image\":\"category\\/resturant.png\",\"opening_one\":\"--\",\"opening_two\":\"--\"},{\"id\":\"5\",\"name\":\"O Portes d'Afrique\",\"date\":\"\",\"address\":\"20 rue Fontaine Gueydon\",\"address2\":\"\",\"zip\":\"97200\",\"country\":\"Martinique\",\"city\":\"Fort-De-France\",\"lat\":\"14.61025\",\"lng\":\"-61.08691670000002\",\"image\":\"image\\/other.jpg\",\"phone\":\"0596 71 97 53\",\"type\":\"free\",\"category\":\"Restaurant\",\"is_free\":\"\",\"speed\":\"1--5\",\"fav_count\":\"0\",\"category_image\":\"category\\/resturant.png\",\"opening_one\":\"--\",\"opening_two\":\"--\"},{\"id\":\"8\",\"name\":\"Paul dco\",\"date\":\"\",\"address\":\"ZI Petite Cocotte\",\"address2\":\"Immble Lapyre \",\"zip\":\"97224\",\"country\":\"Martinique\",\"city\":\"Ducos\",\"lat\":\"14.5250833\",\"lng\":\"-60.98036109999998\",\"image\":\"image\\/8\\/paulcover-sp.jpg\",\"phone\":\"0596 60 33 01\",\"type\":\"paid\",\"category\":\"Mall\",\"is_free\":\"\",\"speed\":\"1--5\",\"fav_count\":\"0\",\"category_image\":\"category\\/ServiceStore.png\",\"opening_one\":\"--\",\"opening_two\":\"--\"},{\"id\":\"16\",\"name\":\"la Petite Banquise\",\"date\":\"\",\"address\":\"27 Rue Professeur Raymond Garcin\",\"address2\":\"\",\"zip\":\"97200\",\"country\":\"Martinique\",\"city\":\"Fort-De-France\",\"lat\":\"14.615066293048637\",\"lng\":\"-61.078726586508196\",\"image\":\"image\\/other.jpg\",\"phone\":\"0596 51 49 63\",\"type\":\"free\",\"category\":\"Other\",\"is_free\":\"\",\"speed\":\"1--5\",\"fav_count\":\"0\",\"category_image\":\"category\\/Wifi-512-white.png\",\"opening_one\":\"Tu - Fri : 7:30 - 18.00\",\"opening_two\":\"Sat - Sun : 8.00 - 13.00\"},{\"id\":\"18\",\"name\":\"Paul Didier\",\"date\":\"\",\"address\":\"15 route de Didier\",\"address2\":\"\",\"zip\":\"97200\",\"country\":\"Martinique\",\"city\":\"Fort-De-France\",\"lat\":\"14.6146111\",\"lng\":\"-61.07858329999999\",\"image\":\"image\\/18\\/paulcover-sp.jpg\",\"phone\":\"05 96 73 16 60\",\"type\":\"airzoon\",\"category\":\"Restaurant\",\"is_free\":\"\",\"speed\":\"1--5\",\"fav_count\":\"0\",\"category_image\":\"category\\/resturant.png\",\"opening_one\":\"--\",\"opening_two\":\"--\"},{\"id\":\"19\",\"name\":\"Quick\",\"date\":\"\",\"address\":\"Centre commercial Perrinon\",\"address2\":\"\",\"zip\":\"97200\",\"country\":\"Martinique\",\"city\":\"Fort-De-France\",\"lat\":\"14.6062778\",\"lng\":\"-61.07050000000004\",\"image\":\"image\\/other.jpg\",\"phone\":\"0596 77 20 55\",\"type\":\"free\",\"category\":\"Fast-food & Snack\",\"is_free\":\"\",\"speed\":\"1--5\",\"fav_count\":\"0\",\"category_image\":\"category\\/fast_food.png\",\"opening_one\":\"--\",\"opening_two\":\"--\"},{\"id\":\"22\",\"name\":\"Food Circus\",\"date\":\"\",\"address\":\"centre commerciale la galleria lamentin\",\"address2\":\"\",\"zip\":\"97232\",\"country\":\"Martinique\",\"city\":\"Trois Ilets\",\"lat\":\"14.6197778\",\"lng\":\"-61.0203611\",\"image\":\"image\\/other.jpg\",\"phone\":\"0596506250\",\"type\":\"free\",\"category\":\"Restaurant\",\"is_free\":\"\",\"speed\":\"1--5\",\"fav_count\":\"0\",\"category_image\":\"category\\/resturant.png\",\"opening_one\":\"--\",\"opening_two\":\"--\"},{\"id\":\"23\",\"name\":\"Le Saladier Bio\",\"date\":\"\",\"address\":\"Zone jambette\",\"address2\":\"\",\"zip\":\"97232\",\"country\":\"Martinique\",\"city\":\"Lamentin\",\"lat\":\"14.6171111\",\"lng\":\"-61.033805599999994\",\"image\":\"image\\/other.jpg\",\"phone\":\"0596 77 88 70 \",\"type\":\"free\",\"category\":\"Restaurant\",\"is_free\":\"\",\"speed\":\"1--5\",\"fav_count\":\"0\",\"category_image\":\"category\\/resturant.png\",\"opening_one\":\"--\",\"opening_two\":\"--\"},{\"id\":\"50\",\"name\":\"Mango Bay SARL\",\"date\":\"\",\"address\":\"Port de plaisance\",\"address2\":\"\",\"zip\":\"97290\",\"country\":\"Martinique\",\"city\":\"Marin\",\"lat\":\"14.4701667\",\"lng\":\"-60.86922219999997\",\"image\":\"image\\/other.jpg\",\"phone\":\"0596 74 60 89\",\"type\":\"free\",\"category\":\"Restaurant\",\"is_free\":\"\",\"speed\":\"1--5\",\"fav_count\":\"0\",\"category_image\":\"category\\/resturant.png\",\"opening_one\":\"--\",\"opening_two\":\"--\"},{\"id\":\"51\",\"name\":\"Zanzibar\",\"date\":\"\",\"address\":\"11 boulevard All\\u00e8gre\",\"address2\":\"Front de mer plage du bourg \",\"zip\":\"97290\",\"country\":\"Martinique\",\"city\":\"Le Marin\",\"lat\":\"14.4696667\",\"lng\":\"-60.865583300000026\",\"image\":\"image\\/other.jpg\",\"phone\":\"0596 74 08 46\",\"type\":\"free\",\"category\":\"Restaurant\",\"is_free\":\"\",\"speed\":\"1--5\",\"fav_count\":\"0\",\"category_image\":\"category\\/resturant.png\",\"opening_one\":\"--\",\"opening_two\":\"--\"},{\"id\":\"69\",\"name\":\"Le Domaine de Saint Aubin\",\"date\":\"2014-12-16\",\"address\":\"Quartier petite rivi\\u00e8re sal\\u00e9e\",\"address2\":\"\",\"zip\":\"97220\",\"country\":\"Martinique\",\"city\":\"Trinit\\u00e9\",\"lat\":\"14.7706389\",\"lng\":\"-60.9821111\",\"image\":\"image\\/other.jpg\",\"phone\":\"0596 36 34 77\",\"type\":\"free\",\"category\":\"Restaurant\",\"is_free\":\"\",\"speed\":\"1--5\",\"fav_count\":\"0\",\"category_image\":\"category\\/resturant.png\",\"opening_one\":\"--\",\"opening_two\":\"--\"},{\"id\":\"72\",\"name\":\"Pignon Nouvelle Vague\",\"date\":\"\",\"address\":\"2 rue Caret\",\"address2\":\"Anse \\u00e0 l'ane \",\"zip\":\"97229\",\"country\":\"Martinique\",\"city\":\"Trois-Ilets\",\"lat\":\"14.5401389\",\"lng\":\"-61.06536110000002\",\"image\":\"image\\/other.jpg\",\"phone\":\"0596 38 30 60\",\"type\":\"free\",\"category\":\"Restaurant\",\"is_free\":\"\",\"speed\":\"1--5\",\"fav_count\":\"0\",\"category_image\":\"category\\/resturant.png\",\"opening_one\":\"--\",\"opening_two\":\"--\"},{\"id\":\"77\",\"name\":\"Kano\",\"date\":\"\",\"address\":\"Rue des bougainvilliers\",\"address2\":\"\",\"zip\":\"97229\",\"country\":\"Martinique\",\"city\":\"Trois-Ilets\",\"lat\":\"14.55325\",\"lng\":\"-61.052722200000005\",\"image\":\"image\\/other.jpg\",\"phone\":\"0596 78 40 33\",\"type\":\"free\",\"category\":\"Bar\",\"is_free\":\"\",\"speed\":\"1--5\",\"fav_count\":\"0\",\"category_image\":\"category\\/bar.png\",\"opening_one\":\"--\",\"opening_two\":\"--\"},{\"id\":\"83\",\"name\":\"Le Jardin des Envies\",\"date\":\"\",\"address\":\"Village de la Poterie \",\"address2\":\"\",\"zip\":\"97229\",\"country\":\"Martinique\",\"city\":\"Trois-Ilets\",\"lat\":\"14.535292555085817\",\"lng\":\"-61.01181565396729\",\"image\":\"image\\/other.jpg\",\"phone\":\"0696 10 34 00\",\"type\":\"free\",\"category\":\"Restaurant\",\"is_free\":\"\",\"speed\":\"1--5\",\"fav_count\":\"0\",\"category_image\":\"category\\/resturant.png\",\"opening_one\":\"--\",\"opening_two\":\"--\"},{\"id\":\"84\",\"name\":\"O'Coup d'Coeur\",\"date\":\"\",\"address\":\"Village de la Poterie \",\"address2\":\"\",\"zip\":\"97229\",\"country\":\"Martinique\",\"city\":\"Trois-Ilets\",\"lat\":\"14.535221926162837\",\"lng\":\"-61.0118901809509\",\"image\":\"image\\/other.jpg\",\"phone\":\"0596 69 70 32\",\"type\":\"free\",\"category\":\"Restaurant\",\"is_free\":\"\",\"speed\":\"1--5\",\"fav_count\":\"0\",\"category_image\":\"category\\/resturant.png\",\"opening_one\":\"--\",\"opening_two\":\"--\"},{\"id\":\"85\",\"name\":\"La Table de Mamy Nounou\",\"date\":\"\",\"address\":\"H\\u00f4tel La Caravelle\",\"address2\":\"Route du Ch\\u00e2teau Dubuc Tartane\",\"zip\":\"97220\",\"country\":\"Martinique\",\"city\":\"Trinit\\u00e9\",\"lat\":\"14.7631667\",\"lng\":\"-60.90769439999997\",\"image\":\"image\\/other.jpg\",\"phone\":\"0596 58 07 32\",\"type\":\"free\",\"category\":\"Restaurant\",\"is_free\":\"\",\"speed\":\"1--5\",\"fav_count\":\"0\",\"category_image\":\"category\\/resturant.png\",\"opening_one\":\"--\",\"opening_two\":\"--\"},{\"id\":\"89\",\"name\":\"Atoumo Lounge\",\"date\":\"\",\"address\":\"Z. I. Champigny\",\"address2\":\"60m \\u00e0 droite apr\\u00e8s ROSETTE\",\"zip\":\"97224\",\"country\":\"Martinique\",\"city\":\"Ducos\",\"lat\":\"14.577834430218537\",\"lng\":\"-60.98804965134275\",\"image\":\"image\\/other.jpg\",\"phone\":\"\",\"type\":\"airzoon\",\"category\":\"Restaurant\",\"is_free\":\"\",\"speed\":\"1--5\",\"fav_count\":\"0\",\"category_image\":\"category\\/resturant.png\",\"opening_one\":\"--\",\"opening_two\":\"--\"},{\"id\":\"92\",\"name\":\"Le Bambou\",\"date\":\"\",\"address\":\"Fond marie reine\",\"address2\":\"\",\"zip\":\"97260\",\"country\":\"Martinique\",\"city\":\"Morne Rouge\",\"lat\":\"14.7770556\",\"lng\":\"-61.13594439999997\",\"image\":\"image\\/other.jpg\",\"phone\":\"0596523994\",\"type\":\"airzoon\",\"category\":\"Restaurant\",\"is_free\":\"\",\"speed\":\"1--5\",\"fav_count\":\"0\",\"category_image\":\"category\\/resturant.png\",\"opening_one\":\"--\",\"opening_two\":\"--\"},{\"id\":\"99\",\"name\":\"Western Union \",\"date\":\"\",\"address\":\"3-7 Rue Joseph Compere\",\"address2\":\"\",\"zip\":\"97200\",\"country\":\"Martinique\",\"city\":\"Fort-de-France\",\"lat\":\"14.6025556\",\"lng\":\"-61.07277779999998\",\"image\":\"image\\/other.jpg\",\"phone\":\"\",\"type\":\"airzoon\",\"category\":\"Other\",\"is_free\":\"\",\"speed\":\"1--5\",\"fav_count\":\"0\",\"category_image\":\"category\\/Wifi-512-white.png\",\"opening_one\":\"--\",\"opening_two\":\"--\"},{\"id\":\"100\",\"name\":\"Zouk TV\",\"date\":\"\",\"address\":\"Rue Garnier Pages\",\"address2\":\"\",\"zip\":\"97200\",\"country\":\"Martinique\",\"city\":\"Fort de France\",\"lat\":\"14.6025833\",\"lng\":\"-61.07255559999999\",\"image\":\"image\\/other.jpg\",\"phone\":\"\",\"type\":\"airzoon\",\"category\":\"Other\",\"is_free\":\"\",\"speed\":\"1--5\",\"fav_count\":\"0\",\"category_image\":\"category\\/Wifi-512-white.png\",\"opening_one\":\"--\",\"opening_two\":\"--\"},{\"id\":\"101\",\"name\":\"The Yellow\",\"date\":\"\",\"address\":\"51 rue victor hugo \",\"address2\":\"\",\"zip\":\"97200\",\"country\":\"Martinique\",\"city\":\"Fort de France\",\"lat\":\"14.604013677515535\",\"lng\":\"-61.070655346310446\",\"image\":\"image\\/other.jpg\",\"phone\":\"\",\"type\":\"airzoon\",\"category\":\"Restaurant\",\"is_free\":\"\",\"speed\":\"1--5\",\"fav_count\":\"0\",\"category_image\":\"category\\/resturant.png\",\"opening_one\":\"--\",\"opening_two\":\"--\"},{\"id\":\"105\",\"name\":\"Maximus\",\"date\":\"\",\"address\":\"les hauts de californie le lamentin\",\"address2\":\"\",\"zip\":\"97232\",\"country\":\"Martinique\",\"city\":\"Lamentin\",\"lat\":\"14.61848353870117\",\"lng\":\"-61.03024313278809\",\"image\":\"image\\/other.jpg\",\"phone\":\"0596501637\",\"type\":\"airzoon\",\"category\":\"Restaurant\",\"is_free\":\"\",\"speed\":\"1--5\",\"fav_count\":\"0\",\"category_image\":\"category\\/resturant.png\",\"opening_one\":\"--\",\"opening_two\":\"--\"},{\"id\":\"294\",\"name\":\"Hotel club marouba\",\"date\":\"2015-05-17\",\"address\":\"quartier choisy le coin\",\"address2\":\"\",\"zip\":\"97221\",\"country\":\"Martinique\",\"city\":\"Carbet\",\"lat\":\"14.6986111\",\"lng\":\"-61.1803056\",\"image\":\"image\\/other.jpg\",\"phone\":\"0596780021\",\"type\":\"free\",\"category\":\"Hotel\",\"is_free\":\"\",\"speed\":\"1--5\",\"fav_count\":\"0\",\"category_image\":\"category\\/hotel1.png\",\"opening_one\":\"--\",\"opening_two\":\"--\"},{\"id\":\"295\",\"name\":\"Beach grill\",\"date\":\"2015-05-17\",\"address\":\"2 all\\u00e9e du d\\u00e9barcad\\u00e8re\",\"address2\":\"\",\"zip\":\"97221\",\"country\":\"Martinique\",\"city\":\"Le Carbet\",\"lat\":\"14.70225\",\"lng\":\"-61.18194440000002\",\"image\":\"image\\/other.jpg\",\"phone\":\"0596783402\",\"type\":\"free\",\"category\":\"Restaurant\",\"is_free\":\"\",\"speed\":\"1--5\",\"fav_count\":\"0\",\"category_image\":\"category\\/resturant.png\",\"opening_one\":\"--\",\"opening_two\":\"--\"},{\"id\":\"296\",\"name\":\"le Jardin des Alizees (Squash hotel)\",\"date\":\"2015-05-17\",\"address\":\"3 boulevard de la marne\",\"address2\":\"\",\"zip\":\"97200\",\"country\":\"Martinique\",\"city\":\"Fort-De-France\",\"lat\":\"14.6048889\",\"lng\":\"-61.082833300000004\",\"image\":\"image\\/other.jpg\",\"phone\":\"0596728080\",\"type\":\"free\",\"category\":\"Restaurant\",\"is_free\":\"\",\"speed\":\"1--5\",\"fav_count\":\"0\",\"category_image\":\"category\\/resturant.png\",\"opening_one\":\"--\",\"opening_two\":\"--\"},{\"id\":\"297\",\"name\":\"Le Totem\",\"date\":\"2015-05-17\",\"address\":\"centre dillon Valmeni\\u00e8re\",\"address2\":\"\",\"zip\":\"97200\",\"country\":\"Martinique\",\"city\":\"Fort-De-France\",\"lat\":\"14.61825\",\"lng\":\"-61.0407778\",\"image\":\"image\\/other.jpg\",\"phone\":\"0596633433\",\"type\":\"free\",\"category\":\"Restaurant\",\"is_free\":\"\",\"speed\":\"1--5\",\"fav_count\":\"0\",\"category_image\":\"category\\/resturant.png\",\"opening_one\":\"--\",\"opening_two\":\"--\"},{\"id\":\"298\",\"name\":\"Villa Factory\",\"date\":\"2015-05-17\",\"address\":\"135 Avenue Condorcet\",\"address2\":\"\",\"zip\":\"97200\",\"country\":\"Martinique\",\"city\":\"Fort-De-France\",\"lat\":\"14.6101389\",\"lng\":\"-61.0862222\",\"image\":\"image\\/other.jpg\",\"phone\":\"0596 56 38 56\",\"type\":\"free\",\"category\":\"Restaurant\",\"is_free\":\"\",\"speed\":\"1--5\",\"fav_count\":\"0\",\"category_image\":\"category\\/resturant.png\",\"opening_one\":\"--\",\"opening_two\":\"--\"},{\"id\":\"299\",\"name\":\"Baguet shop\",\"date\":\"2015-05-17\",\"address\":\"Imm. Leader Price ZI La Laugier\",\"address2\":\"\",\"zip\":\"97232\",\"country\":\"Martinique\",\"city\":\"Lamentin\",\"lat\":\"14.577\",\"lng\":\"-60.9859444\",\"image\":\"image\\/other.jpg\",\"phone\":\"05 96 65 78 23\",\"type\":\"airzoon\",\"category\":\"Restaurant\",\"is_free\":\"\",\"speed\":\"1--5\",\"fav_count\":\"0\",\"category_image\":\"category\\/resturant.png\",\"opening_one\":\"--\",\"opening_two\":\"--\"},{\"id\":\"300\",\"name\":\"O Bl\\u00e9 Noir\",\"date\":\"2015-05-17\",\"address\":\"Port de plaisance bassin tortue\",\"address2\":\"\",\"zip\":\"97290\",\"country\":\"Martinique\",\"city\":\"Marin\",\"lat\":\"14.4708333\",\"lng\":\"-60.86622220000004\",\"image\":\"image\\/other.jpg\",\"phone\":\"0596481201\",\"type\":\"free\",\"category\":\"Restaurant\",\"is_free\":\"\",\"speed\":\"1--5\",\"fav_count\":\"0\",\"category_image\":\"category\\/resturant.png\",\"opening_one\":\"--\",\"opening_two\":\"--\"},{\"id\":\"301\",\"name\":\"Calebasse caf\\u00e9\",\"date\":\"2015-05-17\",\"address\":\"19 boulevard allegre Marin\",\"address2\":\"\",\"zip\":\"97290\",\"country\":\"Martinique\",\"city\":\"Marin\",\"lat\":\"14.4698333\",\"lng\":\"-60.86574999999999\",\"image\":\"image\\/other.jpg\",\"phone\":\"0596 74 91 93\",\"type\":\"free\",\"category\":\"Restaurant\",\"is_free\":\"\",\"speed\":\"1--5\",\"fav_count\":\"0\",\"category_image\":\"category\\/resturant.png\",\"opening_one\":\"--\",\"opening_two\":\"--\"},{\"id\":\"302\",\"name\":\"la Chaudi\\u00e8re\",\"date\":\"2015-05-17\",\"address\":\"19 quartier proprete\",\"address2\":\"\",\"zip\":\"97260\",\"country\":\"Martinique\",\"city\":\"Morne Rouge\",\"lat\":\"14.759\",\"lng\":\"-61.1116667\",\"image\":\"image\\/other.jpg\",\"phone\":\"06 96 43 64 39\",\"type\":\"free\",\"category\":\"Restaurant\",\"is_free\":\"\",\"speed\":\"1--5\",\"fav_count\":\"0\",\"category_image\":\"category\\/resturant.png\",\"opening_one\":\"-\",\"opening_two\":\"-\"},{\"id\":\"303\",\"name\":\"Ti jardin\",\"date\":\"2015-05-17\",\"address\":\"5 domaine de Belfond saint-anne\",\"address2\":\"\",\"zip\":\"\",\"country\":\"Martinique\",\"city\":\"Saint-Anne\",\"lat\":\"14.4438611\",\"lng\":\"-60.87597219999998\",\"image\":\"image\\/other.jpg\",\"phone\":\"0596 76 86 99\",\"type\":\"free\",\"category\":\"Restaurant\",\"is_free\":\"\",\"speed\":\"1--5\",\"fav_count\":\"0\",\"category_image\":\"category\\/resturant.png\",\"opening_one\":\"--\",\"opening_two\":\"--\"},{\"id\":\"304\",\"name\":\"Le Mayday\",\"date\":\"\",\"address\":\"Le Mayday Le Marin\",\"address2\":\"\",\"zip\":\"\",\"country\":\"Martinique\",\"city\":\"Sainte-Anne\",\"lat\":\"14.4668056\",\"lng\":\"-60.864361099999996\",\"image\":\"image\\/other.jpg\",\"phone\":\"\",\"type\":\"free\",\"category\":\"Restaurant\",\"is_free\":\"\",\"speed\":\"1--5\",\"fav_count\":\"0\",\"category_image\":\"category\\/resturant.png\",\"opening_one\":\"--\",\"opening_two\":\"--\"},{\"id\":\"305\",\"name\":\"Club Med Les Boucaniers\",\"date\":\"\",\"address\":\"Pointe du Marin\",\"address2\":\"\",\"zip\":\"97227\",\"country\":\"Martinique\",\"city\":\"Sainte-Anne\",\"lat\":\"14.4477778\",\"lng\":\"-60.88311110000001\",\"image\":\"image\\/other.jpg\",\"phone\":\"\",\"type\":\"free\",\"category\":\"Hotel\",\"is_free\":\"\",\"speed\":\"1--5\",\"fav_count\":\"0\",\"category_image\":\"category\\/hotel1.png\",\"opening_one\":\"--\",\"opening_two\":\"--\"},{\"id\":\"306\",\"name\":\"Hiwear\",\"date\":\"\",\"address\":\"Rue Francois Eus\\u00e8be\",\"address2\":\"\",\"zip\":\"\",\"country\":\"Martinique\",\"city\":\"Le Marin\",\"lat\":\"14.4701111\",\"lng\":\"-60.86422219999997\",\"image\":\"image\\/other.jpg\",\"phone\":\"\",\"type\":\"airzoon\",\"category\":\"Other\",\"is_free\":\"\",\"speed\":\"1--5\",\"fav_count\":\"0\",\"category_image\":\"category\\/Wifi-512-white.png\",\"opening_one\":\"--\",\"opening_two\":\"--\"},{\"id\":\"307\",\"name\":\"Fullmoon\",\"date\":\"\",\"address\":\"-\",\"address2\":\"\",\"zip\":\"97200\",\"country\":\"Martinique\",\"city\":\"Fort de France\",\"lat\":\"14.6124444\",\"lng\":\"-61.07849999999996\",\"image\":\"image\\/other.jpg\",\"phone\":\"\",\"type\":\"airzoon\",\"category\":\"Restaurant\",\"is_free\":\"\",\"speed\":\"1--5\",\"fav_count\":\"0\",\"category_image\":\"category\\/resturant.png\",\"opening_one\":\"--\",\"opening_two\":\"--\"},{\"id\":\"308\",\"name\":\"DoItForMe\",\"date\":\"\",\"address\":\"Tour Lumina\",\"address2\":\"\",\"zip\":\"97200\",\"country\":\"Martinique\",\"city\":\"Fort de France\",\"lat\":\"14.6021944\",\"lng\":\"-61.0733611\",\"image\":\"image\\/other.jpg\",\"phone\":\"\",\"type\":\"airzoon\",\"category\":\"Other\",\"is_free\":\"\",\"speed\":\"1--5\",\"fav_count\":\"0\",\"category_image\":\"category\\/Wifi-512-white.png\",\"opening_one\":\"--\",\"opening_two\":\"--\"},{\"id\":\"309\",\"name\":\"MacDo\",\"date\":\"\",\"address\":\"Iconic fast-food burger & fries chain Rue Victor Schoelcher\",\"address2\":\"\",\"zip\":\"97200\",\"country\":\"Martinique\",\"city\":\"Fort de France\",\"lat\":\"14.6032222\",\"lng\":\"-61.07002779999999\",\"image\":\"image\\/other.jpg\",\"phone\":\"\",\"type\":\"free\",\"category\":\"Fast-food & Snack\",\"is_free\":\"\",\"speed\":\"1--5\",\"fav_count\":\"0\",\"category_image\":\"category\\/fast_food.png\",\"opening_one\":\"--\",\"opening_two\":\"--\"},{\"id\":\"310\",\"name\":\"Le Black Pearl\",\"date\":\"\",\"address\":\"-\",\"address2\":\"\",\"zip\":\"97200\",\"country\":\"Martinique\",\"city\":\"Fort de France\",\"lat\":\"14.6033889\",\"lng\":\"-61.07024999999999\",\"image\":\"image\\/other.jpg\",\"phone\":\"\",\"type\":\"airzoon\",\"category\":\"Restaurant\",\"is_free\":\"\",\"speed\":\"10--15\",\"fav_count\":\"0\",\"category_image\":\"category\\/resturant.png\",\"opening_one\":\"Mon - Sun : 8am - 1am\",\"opening_two\":\"--\"},{\"id\":\"311\",\"name\":\"Kay Ali\",\"date\":\"\",\"address\":\"Village de la Pointe Pnt Faula\",\"address2\":\"\",\"zip\":\"97233\",\"country\":\"Martinique\",\"city\":\"Schoelcher\",\"lat\":\"14.6103333\",\"lng\":\"-61.087611100000004\",\"image\":\"image\\/other.jpg\",\"phone\":\"\",\"type\":\"airzoon\",\"category\":\"Restaurant\",\"is_free\":\"\",\"speed\":\"1--5\",\"fav_count\":\"0\",\"category_image\":\"category\\/resturant.png\",\"opening_one\":\"lun - mar : 10am - 3pm\",\"opening_two\":\"sat - sun : 10am - 4pm\"},{\"id\":\"314\",\"name\":\"La Pura Vida\",\"date\":\"\",\"address\":\"gros raisin\",\"address2\":\"\",\"zip\":\"97228\",\"country\":\"Martinique\",\"city\":\"Sainte-Luce\",\"lat\":\"14.4664722\",\"lng\":\"-60.928027799999995\",\"image\":\"image\\/other.jpg\",\"phone\":\"0596 53 89 35\",\"type\":\"free\",\"category\":\"Restaurant\",\"is_free\":\"\",\"speed\":\"1--5\",\"fav_count\":\"0\",\"category_image\":\"category\\/resturant.png\",\"opening_one\":\"--\",\"opening_two\":\"--\"},{\"id\":\"315\",\"name\":\"La galleria \",\"date\":\"\",\"address\":\"Acajou lamentin\",\"address2\":\"\",\"zip\":\"97232\",\"country\":\"Martinique\",\"city\":\"Lamentin\",\"lat\":\"14.6197778\",\"lng\":\"-61.0203611\",\"image\":\"image\\/other.jpg\",\"phone\":\"0596 50 66 63\",\"type\":\"free\",\"category\":\"Mall\",\"is_free\":\"\",\"speed\":\"1--5\",\"fav_count\":\"0\",\"category_image\":\"category\\/ServiceStore.png\",\"opening_one\":\"--\",\"opening_two\":\"--\"},{\"id\":\"316\",\"name\":\"Le Bar'Oc\",\"date\":\"\",\"address\":\"97 rue du professeur raymond garcin\",\"address2\":\"Route de Didier\",\"zip\":\"97200\",\"country\":\"Martinique\",\"city\":\"Fort-De-France\",\"lat\":\"14.6246944\",\"lng\":\"-61.07888890000004\",\"image\":\"image\\/other.jpg\",\"phone\":\"05 96 48 52 52\",\"type\":\"airzoon\",\"category\":\"Bar\",\"is_free\":\"\",\"speed\":\"1--5\",\"fav_count\":\"0\",\"category_image\":\"category\\/bar.png\",\"opening_one\":\"--\",\"opening_two\":\"--\"},{\"id\":\"317\",\"name\":\"la Yole Bleue\",\"date\":\"\",\"address\":\"centre commercial de cluny Fort-de-France\",\"address2\":\"\",\"zip\":\"97200\",\"country\":\"Martinique\",\"city\":\"Fort-De-France\",\"lat\":\"14.6195\",\"lng\":\"-61.0825556\",\"image\":\"image\\/other.jpg\",\"phone\":\"0596 71 39 07\",\"type\":\"airzoon\",\"category\":\"Fast-food & Snack\",\"is_free\":\"\",\"speed\":\"1--5\",\"fav_count\":\"0\",\"category_image\":\"category\\/fast_food.png\",\"opening_one\":\"--\",\"opening_two\":\"--\"},{\"id\":\"320\",\"name\":\"H\\u00f4tel I'Imp\\u00e9ratrice\",\"date\":\"\",\"address\":\"15 rue de la libert\\u00e9 place de la savane \",\"address2\":\"\",\"zip\":\"97200\",\"country\":\"Martinique\",\"city\":\"Fort-De-France\",\"lat\":\"14.6037778\",\"lng\":\"-61.068472199999974\",\"image\":\"image\\/other.jpg\",\"phone\":\"0596 63 06 82\",\"type\":\"free\",\"category\":\"Hotel\",\"is_free\":\"\",\"speed\":\"1--5\",\"fav_count\":\"0\",\"category_image\":\"category\\/hotel1.png\",\"opening_one\":\"--\",\"opening_two\":\"--\"},{\"id\":\"321\",\"name\":\"Le Deck\",\"date\":\"\",\"address\":\"Palais des Congres Madiana \",\"address2\":\"\",\"zip\":\"97233\",\"country\":\"Martinique\",\"city\":\"Schoelcher\",\"lat\":\"14.6151667\",\"lng\":\"-61.09505560000002\",\"image\":\"image\\/other.jpg\",\"phone\":\"0596 72 15 30\",\"type\":\"free\",\"category\":\"Restaurant\",\"is_free\":\"\",\"speed\":\"1--5\",\"fav_count\":\"0\",\"category_image\":\"category\\/resturant.png\",\"opening_one\":\"--\",\"opening_two\":\"--\"},{\"id\":\"322\",\"name\":\"Office du tourisme de fdf\",\"date\":\"\",\"address\":\"Office du Tourisme\",\"address2\":\"\",\"zip\":\"\",\"country\":\"Martinique\",\"city\":\"Fort-De-France\",\"lat\":\"14.60275\",\"lng\":\"-61.068833299999994\",\"image\":\"image\\/other.jpg\",\"phone\":\"\",\"type\":\"airzoon\",\"category\":\"Other\",\"is_free\":\"\",\"speed\":\"1--5\",\"fav_count\":\"0\",\"category_image\":\"category\\/Wifi-512-white.png\",\"opening_one\":\"8.00 - 17.00\",\"opening_two\":\"--\"},{\"id\":\"323\",\"name\":\"L'Instant Food\",\"date\":\"\",\"address\":\"42 rue ernest deproge \",\"address2\":\"\",\"zip\":\"97200\",\"country\":\"Martinique\",\"city\":\"Fort-De-France\",\"lat\":\"14.6036389\",\"lng\":\"-61.0709167\",\"image\":\"image\\/other.jpg\",\"phone\":\"0596 60 30 30\",\"type\":\"free\",\"category\":\"Restaurant\",\"is_free\":\"\",\"speed\":\"1--5\",\"fav_count\":\"0\",\"category_image\":\"category\\/resturant.png\",\"opening_one\":\"--\",\"opening_two\":\"--\"},{\"id\":\"324\",\"name\":\"Copacabana \",\"date\":\"\",\"address\":\"7 rue les anthuriums\",\"address2\":\"\",\"zip\":\"97229\",\"country\":\"Martinique\",\"city\":\"Trois-Ilets\",\"lat\":\"14.5508056\",\"lng\":\"-61.054472199999964\",\"image\":\"image\\/other.jpg\",\"phone\":\"0596 66 08 92\",\"type\":\"free\",\"category\":\"Restaurant\",\"is_free\":\"\",\"speed\":\"1--5\",\"fav_count\":\"0\",\"category_image\":\"category\\/resturant.png\",\"opening_one\":\"--\",\"opening_two\":\"--\"},{\"id\":\"325\",\"name\":\"Atomic food\",\"date\":\"\",\"address\":\"27 avenue imp\\u00e9ratrice jos\\u00e9phine\",\"address2\":\"\",\"zip\":\"97229\",\"country\":\"Martinique\",\"city\":\"Trois-Ilets\",\"lat\":\"14.5384722\",\"lng\":\"-61.0348611\",\"image\":\"image\\/other.jpg\",\"phone\":\"0596 68 42 11\",\"type\":\"free\",\"category\":\"Restaurant\",\"is_free\":\"\",\"speed\":\"1--5\",\"fav_count\":\"0\",\"category_image\":\"category\\/resturant.png\",\"opening_one\":\"--\",\"opening_two\":\"--\"},{\"id\":\"326\",\"name\":\"H\\u00f4tel Carayou (l'hibiscus)\",\"date\":\"\",\"address\":\"rue du chacha\",\"address2\":\"\",\"zip\":\"97229\",\"country\":\"Martinique\",\"city\":\"Trois-Ilets\",\"lat\":\"14.5578056\",\"lng\":\"-61.05200000000002\",\"image\":\"image\\/other.jpg\",\"phone\":\"0596 66 04 04\",\"type\":\"free\",\"category\":\"Hotel\",\"is_free\":\"\",\"speed\":\"1--5\",\"fav_count\":\"0\",\"category_image\":\"category\\/hotel1.png\",\"opening_one\":\"--\",\"opening_two\":\"--\"},{\"id\":\"327\",\"name\":\"R\\u00e9sidence Cam\\u00e9lia\",\"date\":\"\",\"address\":\"Les hauts de l'anse mitan\",\"address2\":\"\",\"zip\":\"97229\",\"country\":\"Martinique\",\"city\":\"Trois-Ilets\",\"lat\":\"14.5486944\",\"lng\":\"-61.05352779999998\",\"image\":\"image\\/other.jpg\",\"phone\":\"0596 66 05 85\",\"type\":\"free\",\"category\":\"Hotel\",\"is_free\":\"\",\"speed\":\"1--5\",\"fav_count\":\"0\",\"category_image\":\"category\\/hotel1.png\",\"opening_one\":\"--\",\"opening_two\":\"--\"},{\"id\":\"328\",\"name\":\"Lili's Beach\",\"date\":\"\",\"address\":\"H\\u00f4tel la Bateli\\u00e8re \",\"address2\":\"\",\"zip\":\"97233\",\"country\":\"Martinique\",\"city\":\"Schoelcher\",\"lat\":\"14.6055833\",\"lng\":\"-61.09522219999997\",\"image\":\"image\\/other.jpg\",\"phone\":\"0596 42 89 02\",\"type\":\"free\",\"category\":\"Restaurant\",\"is_free\":\"\",\"speed\":\"1--5\",\"fav_count\":\"0\",\"category_image\":\"category\\/resturant.png\",\"opening_one\":\"-\",\"opening_two\":\"--\"},{\"id\":\"329\",\"name\":\"Case Coco\",\"date\":\"\",\"address\":\"front de mer sainte luce\",\"address2\":\"\",\"zip\":\"97228\",\"country\":\"Martinique\",\"city\":\"Sainte-Luce\",\"lat\":\"14.4704444\",\"lng\":\"-60.9220556\",\"image\":\"image\\/other.jpg\",\"phone\":\"0596 62 32 26\",\"type\":\"free\",\"category\":\"Restaurant\",\"is_free\":\"\",\"speed\":\"1--5\",\"fav_count\":\"0\",\"category_image\":\"category\\/resturant.png\",\"opening_one\":\"--\",\"opening_two\":\"--\"},{\"id\":\"330\",\"name\":\"Face \\u00e0 la mer\",\"date\":\"\",\"address\":\"bourg de sainte-luce\",\"address2\":\"\",\"zip\":\"97228\",\"country\":\"Martinique\",\"city\":\"Sainte-Luce\",\"lat\":\"14.4705833\",\"lng\":\"-60.92194440000003\",\"image\":\"image\\/other.jpg\",\"phone\":\"0596 62 27 98\",\"type\":\"free\",\"category\":\"Restaurant\",\"is_free\":\"\",\"speed\":\"1--5\",\"fav_count\":\"0\",\"category_image\":\"category\\/resturant.png\",\"opening_one\":\"--\",\"opening_two\":\"--\"},{\"id\":\"331\",\"name\":\"Pierre et vacances\",\"date\":\"\",\"address\":\"lieu dit pavillon pointe Philippeau\",\"address2\":\"\",\"zip\":\"97228\",\"country\":\"Martinique\",\"city\":\"Sainte-Luce\",\"lat\":\"14.4660556\",\"lng\":\"-60.93355559999998\",\"image\":\"image\\/other.jpg\",\"phone\":\"0596 62 12 62\",\"type\":\"paid\",\"category\":\"Hotel\",\"is_free\":\"\",\"speed\":\"1--5\",\"fav_count\":\"0\",\"category_image\":\"category\\/hotel1.png\",\"opening_one\":\"--\",\"opening_two\":\"--\"},{\"id\":\"332\",\"name\":\"L'Alsace Kay\",\"date\":\"\",\"address\":\"75 rue gabriel p\\u00e9ri\",\"address2\":\"\",\"zip\":\"97250\",\"country\":\"Martinique\",\"city\":\"Saint Pierre\",\"lat\":\"14.7028611\",\"lng\":\"-61.18177779999996\",\"image\":\"image\\/other.jpg\",\"phone\":\"0596 67 53 65\",\"type\":\"free\",\"category\":\"Restaurant\",\"is_free\":\"\",\"speed\":\"1--5\",\"fav_count\":\"0\",\"category_image\":\"category\\/resturant.png\",\"opening_one\":\"--\",\"opening_two\":\"--\"},{\"id\":\"333\",\"name\":\"restaurant la Vague\",\"date\":\"\",\"address\":\"pl Bertin 97250 saint-pierre\",\"address2\":\"\",\"zip\":\"97250\",\"country\":\"Martinique\",\"city\":\"Saint-Pierre\",\"lat\":\"14.7405833\",\"lng\":\"-61.1761944\",\"image\":\"image\\/other.jpg\",\"phone\":\"0596 78 19 54\",\"type\":\"free\",\"category\":\"Restaurant\",\"is_free\":\"\",\"speed\":\"1--5\",\"fav_count\":\"0\",\"category_image\":\"category\\/resturant.png\",\"opening_one\":\"--\",\"opening_two\":\"--\"},{\"id\":\"335\",\"name\":\"L'Espace Gourmand\",\"date\":\"\",\"address\":\"Centre commercial place d'Armes\",\"address2\":\"\",\"zip\":\"97232\",\"country\":\"Martinique\",\"city\":\"Lamentin\",\"lat\":\"14.6140278\",\"lng\":\"-60.99283330000003\",\"image\":\"image\\/other.jpg\",\"phone\":\"0596 51 43 09\",\"type\":\"free\",\"category\":\"Restaurant\",\"is_free\":\"\",\"speed\":\"1--5\",\"fav_count\":\"0\",\"category_image\":\"category\\/resturant.png\",\"opening_one\":\"--\",\"opening_two\":\"--\"},{\"id\":\"336\",\"name\":\"Kay l'Antillais\",\"date\":\"\",\"address\":\"28 avenue des cara\\u00efbes\",\"address2\":\"\",\"zip\":\"97200\",\"country\":\"Martinique\",\"city\":\"Fort-De-France\",\"lat\":\"14.6043611\",\"lng\":\"-61.06641669999999\",\"image\":\"image\\/other.jpg\",\"phone\":\"0596 57 26 44\",\"type\":\"free\",\"category\":\"Restaurant\",\"is_free\":\"\",\"speed\":\"1--5\",\"fav_count\":\"0\",\"category_image\":\"category\\/resturant.png\",\"opening_one\":\"--\",\"opening_two\":\"--\"},{\"id\":\"342\",\"name\":\"la Yole Bleue\",\"date\":\"\",\"address\":\"Rue du G\\u00e9n\\u00e9ral de Gaulle\",\"address2\":\"\",\"zip\":\"97200\",\"country\":\"Martinique\",\"city\":\"Fort-De-France\",\"lat\":\"14.6054722\",\"lng\":\"-61.06900000000002\",\"image\":\"image\\/other.jpg\",\"phone\":\"\",\"type\":\"airzoon\",\"category\":\"Fast-food & Snack\",\"is_free\":\"\",\"speed\":\"1--5\",\"fav_count\":\"0\",\"category_image\":\"category\\/fast_food.png\",\"opening_one\":\"--\",\"opening_two\":\"--\"},{\"id\":\"343\",\"name\":\"MacDo\",\"date\":\"\",\"address\":\"a\",\"address2\":\"\",\"zip\":\"97200\",\"country\":\"Martinique\",\"city\":\"Fort de France\",\"lat\":\"14.6143889\",\"lng\":\"-61.05200000000002\",\"image\":\"image\\/other.jpg\",\"phone\":\"\",\"type\":\"free\",\"category\":\"Fast-food & Snack\",\"is_free\":\"\",\"speed\":\"1--5\",\"fav_count\":\"0\",\"category_image\":\"category\\/fast_food.png\",\"opening_one\":\"--\",\"opening_two\":\"--\"},{\"id\":\"344\",\"name\":\"MacDo\",\"date\":\"\",\"address\":\"a\",\"address2\":\"\",\"zip\":\"97200\",\"country\":\"Martinique\",\"city\":\"Fort de France\",\"lat\":\"14.6178056\",\"lng\":\"-61.08341669999999\",\"image\":\"image\\/other.jpg\",\"phone\":\"\",\"type\":\"free\",\"category\":\"Fast-food & Snack\",\"is_free\":\"\",\"speed\":\"1--5\",\"fav_count\":\"0\",\"category_image\":\"category\\/fast_food.png\",\"opening_one\":\"--\",\"opening_two\":\"--\"},{\"id\":\"345\",\"name\":\"Lina's Caf\\u00e9\",\"date\":\"\",\"address\":\"-\",\"address2\":\"\",\"zip\":\"\",\"country\":\"Martinique\",\"city\":\"Fort-De-France\",\"lat\":\"14.6034167\",\"lng\":\"-61.068833299999994\",\"image\":\"image\\/other.jpg\",\"phone\":\"\",\"type\":\"airzoon\",\"category\":\"Restaurant\",\"is_free\":\"\",\"speed\":\"1--5\",\"fav_count\":\"0\",\"category_image\":\"category\\/resturant.png\",\"opening_one\":\"--\",\"opening_two\":\"--\"},{\"id\":\"346\",\"name\":\"Lina's Caf\\u00e9\",\"date\":\"\",\"address\":\"-\",\"address2\":\"\",\"zip\":\"\",\"country\":\"Martinique\",\"city\":\"Lamentin\",\"lat\":\"14.6088889\",\"lng\":\"-61.000722199999984\",\"image\":\"image\\/other.jpg\",\"phone\":\"\",\"type\":\"airzoon\",\"category\":\"Restaurant\",\"is_free\":\"\",\"speed\":\"1--5\",\"fav_count\":\"0\",\"category_image\":\"category\\/resturant.png\",\"opening_one\":\"--\",\"opening_two\":\"--\"},{\"id\":\"347\",\"name\":\"Lina's Caf\\u00e9\",\"date\":\"\",\"address\":\"-\",\"address2\":\"\",\"zip\":\"\",\"country\":\"Martinique\",\"city\":\"Fort de France\",\"lat\":\"14.6171389\",\"lng\":\"-61.03430560000004\",\"image\":\"image\\/other.jpg\",\"phone\":\"\",\"type\":\"airzoon\",\"category\":\"Restaurant\",\"is_free\":\"\",\"speed\":\"1--5\",\"fav_count\":\"0\",\"category_image\":\"category\\/resturant.png\",\"opening_one\":\"--\",\"opening_two\":\"--\"},{\"id\":\"348\",\"name\":\"Lina's Caf\\u00e9\",\"date\":\"\",\"address\":\"-\",\"address2\":\"\",\"zip\":\"\",\"country\":\"Martinique\",\"city\":\"Lamentin\",\"lat\":\"14.62125\",\"lng\":\"-60.991944399999966\",\"image\":\"image\\/other.jpg\",\"phone\":\"\",\"type\":\"airzoon\",\"category\":\"Restaurant\",\"is_free\":\"\",\"speed\":\"1--5\",\"fav_count\":\"0\",\"category_image\":\"category\\/resturant.png\",\"opening_one\":\"--\",\"opening_two\":\"--\"},{\"id\":\"359\",\"name\":\"Hasta la Pizza\",\"date\":\"2016-02-07\",\"address\":\"Rue Ernest Desproges\",\"address2\":\"\",\"zip\":\"97200\",\"country\":\"Martinique\",\"city\":\"Fort de France\",\"lat\":\"14.602762535235598\",\"lng\":\"-61.069044803421036\",\"image\":\"image\\/other.jpg\",\"phone\":\"0596 73 30 30\",\"type\":\"airzoon\",\"category\":\"Fast-food & Snack\",\"is_free\":\"\",\"speed\":\"10--15\",\"fav_count\":\"0\",\"category_image\":\"category\\/fast_food.png\",\"opening_one\":\"8h - 23h\",\"opening_two\":\"--\"},{\"id\":\"360\",\"name\":\"Hasta la Pizza\",\"date\":\"2016-02-07\",\"address\":\"Centre commercial La Cabresse\",\"address2\":\"\",\"zip\":\"97233\",\"country\":\"Martinique\",\"city\":\"Schoelcher\",\"lat\":\"14.610855360026033\",\"lng\":\"-61.093329523841874\",\"image\":\"image\\/other.jpg\",\"phone\":\"0596 73 30 30\",\"type\":\"airzoon\",\"category\":\"Fast-food & Snack\",\"is_free\":\"\",\"speed\":\"10--15\",\"fav_count\":\"0\",\"category_image\":\"category\\/fast_food.png\",\"opening_one\":\"8h - 23h\",\"opening_two\":\"--\"},{\"id\":\"363\",\"name\":\"Cyberbase des Trois Ilets\",\"date\":\"2016-02-07\",\"address\":\"Rue Bontemps\",\"address2\":\"\",\"zip\":\"97229\",\"country\":\"Martinique\",\"city\":\"Trois Ilets\",\"lat\":\"14.538705042894213\",\"lng\":\"-61.03483054522707\",\"image\":\"image\\/other.jpg\",\"phone\":\"0596 58 11 71\",\"type\":\"airzoon\",\"category\":\"Other\",\"is_free\":\"\",\"speed\":\"5--10\",\"fav_count\":\"0\",\"category_image\":\"category\\/Wifi-512-white.png\",\"opening_one\":\"--\",\"opening_two\":\"--\"},{\"id\":\"364\",\"name\":\"Lick\",\"date\":\"2016-02-07\",\"address\":\"Rue de la R\\u00e9publique\",\"address2\":\"Cot\\u00e9 Cour Perrinon\",\"zip\":\"97200\",\"country\":\"Martinique\",\"city\":\"Fort de France\",\"lat\":\"14.605259455224179\",\"lng\":\"-61.07025447968675\",\"image\":\"image\\/other.jpg\",\"phone\":\"0596533291\",\"type\":\"airzoon\",\"category\":\"Other\",\"is_free\":\"\",\"speed\":\"5--10\",\"fav_count\":\"0\",\"category_image\":\"category\\/Wifi-512-white.png\",\"opening_one\":\"lun-ven : 08:30 - 17:30\",\"opening_two\":\"sam:\\t 08:30 - 13:30\"},{\"id\":\"365\",\"name\":\"Villa Go\\u00fbt et Saveurs\",\"date\":\"2016-02-07\",\"address\":\"2 Route du G\\u00e9n\\u00e9ral Brosset\",\"address2\":\"\",\"zip\":\"97200\",\"country\":\"Martinique\",\"city\":\"Fort de France\",\"lat\":\"14.614104864591452\",\"lng\":\"-61.06476399783327\",\"image\":\"image\\/other.jpg\",\"phone\":\"0596541036\",\"type\":\"airzoon\",\"category\":\"Restaurant\",\"is_free\":\"\",\"speed\":\"5--10\",\"fav_count\":\"0\",\"category_image\":\"category\\/resturant.png\",\"opening_one\":\"mar-ven: 12:00 -15:00 \\/ 19:00 - 22:30\",\"opening_two\":\"sam:\\t 19:00 - 22:30\"},{\"id\":\"366\",\"name\":\"Coffee And..\",\"date\":\"2016-02-07\",\"address\":\"Centre commercial Le Patio\",\"address2\":\"\",\"zip\":\"97233 Schoelcher\",\"country\":\"Martinique\",\"city\":\"97233\",\"lat\":\"14.616188986396145\",\"lng\":\"-61.08460429791643\",\"image\":\"image\\/other.jpg\",\"phone\":\" 0596 51 80 52\",\"type\":\"airzoon\",\"category\":\"Other\",\"is_free\":\"\",\"speed\":\"5--10\",\"fav_count\":\"0\",\"category_image\":\"category\\/Wifi-512-white.png\",\"opening_one\":\"--\",\"opening_two\":\"--\"},{\"id\":\"367\",\"name\":\"Karissima\",\"date\":\"2016-02-07\",\"address\":\"Centre commercial Le Patio de Cluny\",\"address2\":\"\",\"zip\":\"97233\",\"country\":\"Martinique\",\"city\":\"Schoelcher\",\"lat\":\"14.616318756713973\",\"lng\":\"-61.084537242691056\",\"image\":\"image\\/other.jpg\",\"phone\":\"0596 57 84 86\",\"type\":\"airzoon\",\"category\":\"Pancakes & Waffles\",\"is_free\":\"\",\"speed\":\"5--10\",\"fav_count\":\"0\",\"category_image\":\"category\\/PanCake&Waffles1@2x.png\",\"opening_one\":\"--\",\"opening_two\":\"--\"},{\"id\":\"368\",\"name\":\"Centre commerial Cour Perrinon\",\"date\":\"2016-02-07\",\"address\":\"18 Rue Moreau de Jonnes\",\"address2\":\"\",\"zip\":\"97200\",\"country\":\"Martinique\",\"city\":\"Fort de France\",\"lat\":\"14.60598101310024\",\"lng\":\"-61.07049856070711\",\"image\":\"image\\/other.jpg\",\"phone\":\"05 96 73 71 82\",\"type\":\"airzoon\",\"category\":\"Mall\",\"is_free\":\"\",\"speed\":\"10--15\",\"fav_count\":\"0\",\"category_image\":\"category\\/ServiceStore.png\",\"opening_one\":\"lun-sam: 8h - 19h\",\"opening_two\":\"--\"},{\"id\":\"371\",\"name\":\"Villa des Lucioles \",\"date\":\"\",\"address\":\"16-34\\/Fort-de-France BayMartinique\",\"address2\":\"\",\"zip\":\"\",\"country\":\"Martinique\",\"city\":\"Fort de France\",\"lat\":\"14.608050450387426\",\"lng\":\"-61.08032132484436\",\"image\":\"image\\/371\\/image1462034413630.png\",\"phone\":\"\",\"type\":\"airzoon\",\"category\":\"Restaurant\",\"is_free\":\"\",\"speed\":\"1--5\",\"fav_count\":\"0\",\"category_image\":\"category\\/resturant.png\",\"opening_one\":\"--\",\"opening_two\":\"--\"},{\"id\":\"372\",\"name\":\"Golf International de St Fran\\u00e7ois\",\"date\":\"2016-06-13\",\"address\":\"Golf\",\"address2\":\"\",\"zip\":\"\",\"country\":\"Guadeloupe\",\"city\":\"Saint Fran\\u00e7ois\",\"lat\":\"16.255097934741926\",\"lng\":\"-61.266326640884415\",\"image\":\"image\\/other.jpg\",\"phone\":\"\",\"type\":\"airzoon\",\"category\":\"Other\",\"is_free\":\"\",\"speed\":\"5--10\",\"fav_count\":\"0\",\"category_image\":\"category\\/Wifi-512-white.png\",\"opening_one\":\"--\",\"opening_two\":\"--\"},{\"id\":\"373\",\"name\":\"Gare Maritime de St Fran\\u00e7ois\",\"date\":\"2016-06-13\",\"address\":\"St Fran\\u00e7ois\",\"address2\":\"\",\"zip\":\"\",\"country\":\"Guadeloupe\",\"city\":\"Saint Fran\\u00e7ois\",\"lat\":\"16.250390801070417\",\"lng\":\"-61.27221677188112\",\"image\":\"image\\/other.jpg\",\"phone\":\"\",\"type\":\"airzoon\",\"category\":\"Other\",\"is_free\":\"\",\"speed\":\"10--15\",\"fav_count\":\"0\",\"category_image\":\"category\\/Wifi-512-white.png\",\"opening_one\":\"--\",\"opening_two\":\"--\"},{\"id\":\"374\",\"name\":\"Place Schoelcher\",\"date\":\"2016-07-18\",\"address\":\"Place Schoelcher\",\"address2\":\"\",\"zip\":\"97180\",\"country\":\"Guadeloupe\",\"city\":\"Sainte-Anne\",\"lat\":\"16.225102191650308\",\"lng\":\"-61.38548645858003\",\"image\":\"image\\/374\\/sainteanne-cover-fb.jpg\",\"phone\":\"\",\"type\":\"airzoon\",\"category\":\"Other\",\"is_free\":\"\",\"speed\":\"10--15\",\"fav_count\":\"0\",\"category_image\":\"category\\/Wifi-512-white.png\",\"opening_one\":\"--\",\"opening_two\":\"--\"},{\"id\":\"375\",\"name\":\"Burger King FDF\",\"date\":\"\",\"address\":\"201\\/Fort-de-France BayMartinique\",\"address2\":\"\",\"zip\":\"\",\"country\":\"Martinique\",\"city\":\"Fort de France\",\"lat\":\"14.611887886730885\",\"lng\":\"-61.05328653961908\",\"image\":\"image\\/375\\/image1469294459464.png\",\"phone\":\"\",\"type\":\"free\",\"category\":\"Fast-food & Snack\",\"is_free\":\"\",\"speed\":\"1--5\",\"fav_count\":\"0\",\"category_image\":\"category\\/fast_food.png\",\"opening_one\":\"--\",\"opening_two\":\"--\"}]";
    }

}
