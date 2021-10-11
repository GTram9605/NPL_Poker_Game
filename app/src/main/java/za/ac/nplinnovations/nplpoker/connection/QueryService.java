package za.ac.nplinnovations.nplpoker.connection;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Query;
import za.ac.nplinnovations.nplpoker.connection.entities.MainResponse;

public interface QueryService {
    @Headers({"Accept: application/json",
            "Content-Type: application/json"})
    @GET( "texas_holdem/")
    Call<MainResponse> checkWinners(@Query("cc") String cards, @Query("pc[]") String comboOne, @Query("pc[]") String comboTwo,
        @Query("pc[]") String comboThree, @Query("pc[]") String comboFour);
}
