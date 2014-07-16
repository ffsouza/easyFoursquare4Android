package br.com.condesales;

import android.app.Activity;
import android.content.SharedPreferences;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

import br.com.condesales.constants.FoursquareConstants;
import br.com.condesales.criterias.CheckInCriteria;
import br.com.condesales.criterias.TipsCriteria;
import br.com.condesales.criterias.TrendingVenuesCriteria;
import br.com.condesales.criterias.VenuesCriteria;
import br.com.condesales.listeners.RequestListener;
import br.com.condesales.models.Checkin;
import br.com.condesales.models.Tip;
import br.com.condesales.models.User;
import br.com.condesales.models.Venue;
import br.com.condesales.models.Venues;
import br.com.condesales.tasks.checkins.CheckInRequest;
import br.com.condesales.tasks.tips.TipsNearbyRequest;
import br.com.condesales.tasks.users.GetCheckInsRequest;
import br.com.condesales.tasks.users.GetFriendsRequest;
import br.com.condesales.tasks.users.GetUserVenuesHistoryRequest;
import br.com.condesales.tasks.users.SelfInfoRequest;
import br.com.condesales.tasks.venues.FoursquareTrendingVenuesNearbyRequest;
import br.com.condesales.tasks.venues.FoursquareVenueDetailsRequest;
import br.com.condesales.tasks.venues.FoursquareVenuesNearbyRequest;

/**
 * Class to handle methods used to perform requests to FoursquareAPI and respond
 * SYNChronously.
 *
 * @author Felipe Conde <condesales@gmail.com>
 */
public class EasyFoursquare {

    private Activity mActivity;
    private String mAccessToken = "";

    public EasyFoursquare(Activity activity) {
        mActivity = activity;
    }

    /**
     * Requests the access to API
     */
    public void requestAccess(RequestListener<String> listener) {
        if (!hasAccessToken()) {
            loginDialog(listener);
        } else {
            listener.onSuccess(getAccessToken());
        }
    }

    /**
     * Requests logged user information asynchronously.
     *
     * @return The user information
     */
    public User getUserInfo() {
        SelfInfoRequest request = new SelfInfoRequest();
        request.execute(getAccessToken());
        User user = null;
        try {
            user = request.get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        return user;
    }

    /**
     * Requests nearby Venues.
     *
     * @param criteria The criteria to your search request
     */
    public ArrayList<Venue> getVenuesNearby(VenuesCriteria criteria) {
        FoursquareVenuesNearbyRequest request = new FoursquareVenuesNearbyRequest(criteria);
        request.execute(getAccessToken());
        ArrayList<Venue> venues = new ArrayList<Venue>();
        try {
            venues = request.get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        return venues;
    }

    /**
     * Requests nearby Tip.
     *
     * @param criteria The criteria to your search request
     */
    public ArrayList<Tip> getTipsNearby(TipsCriteria criteria) {
        TipsNearbyRequest request = new TipsNearbyRequest(criteria);
        request.execute(getAccessToken());
        ArrayList<Tip> tips = new ArrayList<Tip>();
        try {
            tips = request.get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        return tips;
    }


    /**
     * Requests nearby Venues that are trending.
     *
     * @param criteria The criteria to your search request
     */
    public ArrayList<Venue> getTrendingVenuesNearby(TrendingVenuesCriteria criteria) {
        FoursquareTrendingVenuesNearbyRequest request = new FoursquareTrendingVenuesNearbyRequest(criteria);
        request.execute(getAccessToken());
        ArrayList<Venue> venues = new ArrayList<Venue>();
        try {
            venues = request.get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        return venues;
    }


    public void getVenueDetail(String venueID) {
        FoursquareVenueDetailsRequest request = new FoursquareVenueDetailsRequest(venueID);
        request.execute(getAccessToken());
    }

    /**
     * Checks in at a venue.
     *
     * @param criteria The criteria to your search request
     */
    public Checkin checkIn(CheckInCriteria criteria) {
        CheckInRequest request = new CheckInRequest(criteria);
        request.execute(getAccessToken());
        Checkin checkin = null;
        try {
            checkin = request.get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        return checkin;
    }

    public ArrayList<Checkin> getcheckIns() {
        GetCheckInsRequest request = new GetCheckInsRequest();
        request.execute(getAccessToken());
        ArrayList<Checkin> checkins = null;
        try {
            checkins = request.get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        return checkins;
    }

    public ArrayList<Checkin> getcheckIns(String userID) {
        GetCheckInsRequest request = new GetCheckInsRequest(userID);
        request.execute(getAccessToken());
        ArrayList<Checkin> checkins = null;
        try {
            checkins = request.get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        return checkins;
    }

    public ArrayList<User> getFriends() {
        GetFriendsRequest request = new GetFriendsRequest();
        request.execute(getAccessToken());
        ArrayList<User> users = null;
        try {
            users = request.get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        return users;
    }

    public ArrayList<Venues> getVenuesHistory() {
        GetUserVenuesHistoryRequest request = new GetUserVenuesHistoryRequest();
        request.execute(getAccessToken());
        ArrayList<Venues> venues = null;
        try {
            venues = request.get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        return venues;
    }

    public ArrayList<Venues> getVenuesHistory(String userID) {
        GetUserVenuesHistoryRequest request = new GetUserVenuesHistoryRequest(userID);
        request.execute(getAccessToken());
        ArrayList<Venues> venues = null;
        try {
            venues = request.get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        return venues;
    }

    public ArrayList<User> getFriends(String userID) {
        GetFriendsRequest request = new GetFriendsRequest(userID);
        request.execute(getAccessToken());
        ArrayList<User> users = null;
        try {
            users = request.get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        return users;
    }

    private boolean hasAccessToken() {
        String token = getAccessToken();
        return !token.equals("");
    }

    /**
     * Gets the access token used to perform requests.
     *
     * @return the token
     */
    private String getAccessToken() {
        if (mAccessToken.equals("")) {
            SharedPreferences settings = mActivity.getSharedPreferences(
                    FoursquareConstants.SHARED_PREF_FILE, 0);
            mAccessToken = settings.getString(FoursquareConstants.ACCESS_TOKEN,
                    "");
        }
        return mAccessToken;
    }

    /**
     * Requests the Foursquare login though a dialog.
     */
    private void loginDialog(RequestListener<String> listener) {
        String url = "https://foursquare.com/oauth2/authenticate"
                + "?client_id=" + FoursquareConstants.CLIENT_ID
                + "&response_type=code" + "&redirect_uri="
                + FoursquareConstants.CALLBACK_URL;

        FoursquareDialog mDialog = new FoursquareDialog(mActivity, url, listener);
        mDialog.show();
    }


}
