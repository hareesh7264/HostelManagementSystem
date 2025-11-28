package com.hostelmgmt.service;

import com.hostelmgmt.dto.RoomDto;
import com.hostelmgmt.entity.Room;

import java.util.List;

public interface RoomService {

    RoomDto createRoom(RoomDto dto);
    RoomDto updateRoom(Long roomId, RoomDto dto);
    void deleteRoom(Long roomId);
    RoomDto getRoom(Long roomId);
    List<RoomDto> getAllRooms();
    List<RoomDto> getRoomsByFloor(int floor);

}

