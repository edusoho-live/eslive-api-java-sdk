package com.edusoho.live.sdk;

import com.edusoho.live.sdk.common.BooleanResponse;
import com.edusoho.live.sdk.common.Pager;
import com.edusoho.live.sdk.model.*;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class EsLiveApiClientTests {

    private EsliveApiClient client;

    private RoomSimple testRoom;

    public EsLiveApiClientTests() {
        ClientConfig config = new ClientConfig();
        config.setServer("live-dev.edusoho.cn");
        config.setAccessKey("flv_self_aliyun");
        config.setSecretKey("testSecretKey4");
        client = new EsliveApiClient(config);

        testRoom = createTestRoom();
    }

    @Test void roomCreate() {
        RoomCreateParams params = new RoomCreateParams();
        params.setName("Java API SDK Unit Test - " + System.currentTimeMillis());
        params.setStartAt(System.currentTimeMillis() + 600000);
        params.setEndAt(System.currentTimeMillis() + 1200000);

        RoomSimple room = client.roomCreate(params);

        assertNotNull(room.getId());
        assertEquals(params.getName(), room.getName());
        assertEquals(params.getStartAt(), room.getStartAt());
        assertEquals(params.getEndAt(), room.getEndAt());
    }

    @Test void roomGet() {
        Room room = client.roomGet(testRoom.getId());
        assertEquals(testRoom.getId(), room.getId());
    }

    @Test void roomUpdate() {
        RoomUpdateParams params = new RoomUpdateParams();
        params.setId(testRoom.getId());
        params.setName(testRoom.getName() + "-" + System.currentTimeMillis());
        params.setStartAt(testRoom.getStartAt() + 600000);
        params.setEndAt(testRoom.getEndAt() + 600000);

        RoomSimple updated = client.roomUpdate(params);

        assertEquals(params.getName(), updated.getName());
        assertEquals(params.getStartAt(), updated.getStartAt());
        assertEquals(params.getEndAt(), updated.getEndAt());
    }

    @Test void roomClose() {
        RoomSimple room = createTestRoom();
        BooleanResponse closed = client.roomClose(room.getId());

        assertTrue(closed.getOk());
    }

    @Test void roomDelete() {
        RoomSimple room = createTestRoom();
        BooleanResponse deleted = client.roomDelete(room.getId());

        assertTrue(deleted.getOk());
    }

    @Test void memberList() {
        MemberListParams params = new MemberListParams();
        params.setRoomId(testRoom.getId());
        params.setOffset(0L);
        params.setLimit(10L);
        Pager<Member> members = client.memberList(params);

        assertEquals(0, members.getData().size());
        assertEquals(0, members.getTotal());
    }

    @Test void memberListVisits() {
        MemberListVisitsParams params = new MemberListVisitsParams();
        params.setRoomId(testRoom.getId());
        params.setOffset(0L);
        params.setLimit(10L);
        Pager<MemberVisit> members = client.memberListVisits(params);

        assertEquals(0, members.getData().size());
        assertEquals(0, members.getTotal());
    }

//    @Test void replayGet() {
//        var replay = client.replayGet(testRoom.getId());
//    }
//
//    @Test void replayGets() {
//        var roomIds = List.of(1L, 2L, 3L);
//        var replays = client.replayGets(roomIds);
//    }
//
//    @Test void replayDelete() {
//        var deleted = client.replayDelete(testRoom.getId());
//    }

    private RoomSimple createTestRoom() {
        RoomCreateParams params = new RoomCreateParams();
        params.setName("Java API SDK Unit Test - " + System.currentTimeMillis());
        params.setStartAt(System.currentTimeMillis() + 600000);
        params.setEndAt(System.currentTimeMillis() + 1200000);

        return client.roomCreate(params);
    }
}
