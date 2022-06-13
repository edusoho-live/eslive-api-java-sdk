package com.edusoho.live.sdk;

import com.edusoho.live.sdk.common.BooleanResponse;
import com.edusoho.live.sdk.common.Pager;
import com.edusoho.live.sdk.model.*;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class EsLiveApiClientTests {

    private final EsliveApiClient client;

    private final Room testRoom;

    private final Logger log;

    public EsLiveApiClientTests() {
        log = LoggerFactory.getLogger(EsliveApiClient.class);

        String accessKey = System.getenv("ESLIVE_ACCESS_KEY");
        String secretKey = System.getenv("ESLIVE_SECRET_KEY");

        log.info("accessKey: {}, secretKey: {}", accessKey, secretKey);

        ClientConfig config = new ClientConfig();
        config.setServer("live-dev.edusoho.cn");
        config.setAccessKey(accessKey);
        config.setSecretKey(secretKey);
        config.setDebug(false);
        client = new EsliveApiClient(config);

        testRoom = createTestRoom();
    }

    @Test void roomGet() {
        Room room = client.roomGet(testRoom.getId());

        log.info("room get: {}", room);

        assertEquals(testRoom.getId(), room.getId());
    }

    @Test void roomCreate() {
        RoomCreateParams params = new RoomCreateParams();
        params.setName("Java API SDK Unit Test - " + System.currentTimeMillis());
        params.setStartAt(System.currentTimeMillis() + 600000);
        params.setEndAt(System.currentTimeMillis() + 1200000);

        Room room = client.roomCreate(params);

        log.info("room created {}", room);

        assertNotNull(room.getId());
        assertEquals(params.getName(), room.getName());
        assertEquals(params.getStartAt(), room.getStartAt());
        assertEquals(params.getEndAt(), room.getEndAt());
    }

    @Test void roomUpdate() {
        RoomUpdateParams params = new RoomUpdateParams();
        params.setId(testRoom.getId());
        params.setName(testRoom.getName() + "-" + System.currentTimeMillis());
        params.setStartAt(testRoom.getStartAt() + 600000);
        params.setEndAt(testRoom.getEndAt() + 600000);

        Room updated = client.roomUpdate(params);

        log.info("room updated {}", updated);

        assertEquals(params.getName(), updated.getName());
        assertEquals(params.getStartAt(), updated.getStartAt());
        assertEquals(params.getEndAt(), updated.getEndAt());
    }

    @Test void roomClose() {
        Room room = createTestRoom();
        BooleanResponse closed = client.roomClose(room.getId());

        assertTrue(closed.getOk());
    }

    @Test void roomDelete() {
        Room room = createTestRoom();
        BooleanResponse deleted = client.roomDelete(room.getId());

        assertTrue(deleted.getOk());
    }

    @Test void memberList() {
        MemberListParams params = new MemberListParams();
        params.setRoomId(testRoom.getId());
        params.setPage(0L);
        params.setSize(20L);
        Pager<Member> members = client.memberList(params);

        assertEquals(0, members.getData().size());
        assertEquals(0, members.getTotal());
    }

    @Test void memberListVisits() {
        MemberListVisitsParams params = new MemberListVisitsParams();
        params.setRoomId(testRoom.getId());
        params.setPage(0L);
        params.setSize(10L);
        Pager<MemberVisit> members = client.memberListVisits(params);

        assertEquals(0, members.getData().size());
        assertEquals(0, members.getTotal());
    }

    @Test void replayGet() {
        Replay replay = client.replayGet(25964L);
        log.info("replay get: {}", replay);
    }

    @Test void replayGets() {
        ArrayList<Long> roomIds = new ArrayList<>();
        roomIds.add(1L);
        roomIds.add(2L);
        roomIds.add(25964L);

        List<Replay> replays = client.replayGets(roomIds);

        log.info("replay gets: {}", replays);

        assertEquals(1, replays.size());
    }

    @Test void replayDelete() {
        BooleanResponse deleted = client.replayDelete(25964L);

        log.info("replay deleted: {}", deleted);
    }

    @Test void roomGetEnterUrl() {
        String url = client.roomGetEnterUrl(testRoom.getId(), 1L, "Test", Role.VIEWER);

        log.info("room get enter url: {}", url);

        assertNotNull(url);
    }

    @Test void replayGetEnterUrl() {
        String url = client.replayGetEnterUrl(testRoom.getId(), 1L, "Test", Role.VIEWER);

        log.info("replay get enter url: {}", url);

        assertNotNull(url);
    }

    private Room createTestRoom() {
        RoomCreateParams params = new RoomCreateParams();
        params.setName("Java API SDK Unit Test - " + System.currentTimeMillis());
        params.setStartAt(System.currentTimeMillis() + 600000);
        params.setEndAt(System.currentTimeMillis() + 1200000);

        return client.roomCreate(params);
    }
}
