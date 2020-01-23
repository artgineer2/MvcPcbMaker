package com.mvcpcbmaker.models.board;

import javax.json.JsonObject;

import com.mvcpcbmaker.utilstructs.BoardLayout;

public interface Board {
	public void initBoard(String name, int columns, JsonObject boardData);
	public JsonObject getBoardLayout();
	public void cleanOutObjectData();
}
