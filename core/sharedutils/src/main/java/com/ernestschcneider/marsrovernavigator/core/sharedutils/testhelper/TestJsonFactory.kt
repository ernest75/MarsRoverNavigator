package com.ernestschcneider.marsrovernavigator.core.sharedutils.testhelper

import org.json.JSONObject

object TestJsonFactory {

    fun createRoverJson(
        plateauX: Int = 5,
        plateauY: Int = 5,
        roverX: Int = 0,
        roverY: Int = 0,
        direction: String = "N",
        movements: String = "L"
    ): JSONObject {
        val jsonString = """
        {
          "topRightCorner": {
            "x": $plateauX,
            "y": $plateauY
          },
          "roverPosition": {
            "x": $roverX,
            "y": $roverY
          },
          "roverDirection": "$direction",
          "movements": "$movements"
        }
        """.trimIndent()

        return JSONObject(jsonString)
    }
}
