package com.edusoho.live.sdk;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.edusoho.live.sdk.common.*;
import com.edusoho.live.sdk.model.*;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import okhttp3.*;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class EsliveApiClient {

    private static final String POST = "POST";

    private static final String GET = "GET";

    private static final String userAgent = "ESLiveApiJavaSDK/1.0.0";

    private static final MediaType JSON_TYPE
            = MediaType.get("application/json; charset=utf-8");

    private final OkHttpClient client = new OkHttpClient();

    private final Gson gson = new Gson();

    private final String accessKey;

    private final String secretKey;

    private final String server;

    public EsliveApiClient(ClientConfig config) {
        accessKey = config.getAccessKey();
        secretKey = config.getSecretKey();
        server = Utils.isEmpty(config.getServer()) ? "live.edusoho.com" : config.getServer();
    }

    public Room roomGet(Long id) {
        Map<String, String> params = new HashMap<>();
        params.put("id", id.toString());

        return get("/room/get", params, Room.class);
    }

    public RoomSimple roomCreate(RoomCreateParams params) {
        return post("/room/create", params, RoomSimple.class);
    }

    public RoomSimple roomUpdate(RoomUpdateParams params) {
        return post( "/room/update", params, RoomSimple.class);
    }

    public BooleanResponse roomClose(Long id) {
        Map<String, String> params = new HashMap<>();
        params.put("id", id.toString());
        return post( "/room/close", params, BooleanResponse.class);
    }

    public BooleanResponse roomDelete(Long id) {
        Map<String, String> params = new HashMap<>();
        params.put("id", id.toString());
        return post("/room/delete", params, BooleanResponse.class);
    }

    public Pager<Member> memberList(MemberListParams params) {
        Type pagerType = new TypeToken<Pager<Member>>(){}.getType();
        return get("/member/list", params, pagerType);
    }

    public Pager<MemberVisit> memberListVisits(MemberListVisitsParams params) {
        Type pagerType = new TypeToken<Pager<MemberVisit>>(){}.getType();
        return get("/member/listVisits", params, pagerType);
    }

    public Replay replayGet(Long roomId) {
        Map<String, String> params = new HashMap<>();
        params.put("roomId", roomId.toString());

        return get("/replay/get", params, Replay.class);
    }

    public List<Replay> replayGets(List<Long> roomIds) {
        String roomIdsStr = roomIds.stream().map(Object::toString).collect(Collectors.joining(","));

        Map<String, String> params = new HashMap<>();
        params.put("roomIds", roomIdsStr);

        Type listType = new TypeToken<List<MemberVisit>>(){}.getType();
        return get("/replay/gets", params, listType);
    }

    public BooleanResponse replayDelete(Long roomId) {
        Map<String, String> params = new HashMap<>();
        params.put("roomId", roomId.toString());
        return post("/replay/delete", params, BooleanResponse.class);
    }

    public String roomGetEnterUrl(Long roomId, Long userId, String name, Role role) {
        String token = JWT.create()
                .withKeyId(accessKey)
                .withIssuer("live client api")
                .withClaim("rid", roomId)
                .withClaim("uid", userId)
                .withClaim("name",name)
                .withClaim("role", role.name().toLowerCase())
                .withExpiresAt(new Date(System.currentTimeMillis() + 1800000))
                .sign(Algorithm.HMAC256(secretKey));

        return "https://" + server + "/h5/room/" + roomId.toString() + "/enter?token=" + token;
    }

    private <T> T get(String uri, QueryParams params, Type responseClass) {
        uri = Utils.appendQueryParams(uri, params);
        return request(GET, uri, null, responseClass);
    }

    private <T> T get(String uri, Map<String, String> params, Type responseClass) {
        uri = Utils.appendQueryParams(uri, params);
        return request(GET, uri, null, responseClass);
    }

    private <T> T post(String uri, Object params, Type responseClass) {
        return request(POST, uri, params, responseClass);
    }

    private <T> T request(String method, String uri, Object params, Type responseClass) {
        String url = "https://" + server + "/api-v2" + uri;

        String token = JWT.create()
                .withKeyId(accessKey)
                .withIssuer("live api")
                .withExpiresAt(new Date(System.currentTimeMillis() + 300000)) //5分钟
                .sign(Algorithm.HMAC256(secretKey));

        Request.Builder req = new Request.Builder()
                .url(url)
                .header("User-Agent", userAgent)
                .header("Authorization","Bearer " + token);

        if (POST.equals(method)) {
            String body = gson.toJson(params);
            req.post(RequestBody.create(body, JSON_TYPE));
        }

        Response response;
        try {
            response = client.newCall(req.build()).execute();
        } catch (IOException e) {
            throw new EsliveApiException("CLIENT_REQUEST_FAILED", e.getMessage());
        }

        if (response.body() == null) {
            throw new EsliveApiException("CLIENT_RESPONSE_FAILED", "Response body is null");
        }

        String result;
        try {
            result = response.body().string();
        } catch (IOException e) {
            throw new EsliveApiException("CLIENT_RESPONSE_FAILED", e.getMessage());
        }

        if (Utils.isEmpty(result)) {
            throw new EsliveApiException("CLIENT_RESPONSE_FAILED", "Response body is empty");
        }

        if (response.isSuccessful()) {
            return gson.fromJson(result, responseClass);
        } else {
            ErrorResponse error = gson.fromJson(result, ErrorResponse.class);
            throw new EsliveApiException(error.getCode(), error.getMessage());
        }
    }
}
