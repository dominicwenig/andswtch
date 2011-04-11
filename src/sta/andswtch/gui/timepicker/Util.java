/*
 * Copyright (C) 2009 Andago Ingenieria
 * Author: Alvaro del Castillo, adelcastillo@andago.com
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package sta.andswtch.gui.timepicker;

import android.text.format.Time;

public class Util {
	public static String pad(int c) {
		if (c >= 10)
			return String.valueOf(c);
		else
			return "0" + String.valueOf(c);
	}

	public static String secToFormattedEndTimeFromNow(int sumSeconds) {
		Time now = new Time();
		now.setToNow();
		Time endTime = new Time();
		endTime.set(now.toMillis(true) + sumSeconds * 1000);
		return endTime.format("%H:%M:%S");
	}

}
