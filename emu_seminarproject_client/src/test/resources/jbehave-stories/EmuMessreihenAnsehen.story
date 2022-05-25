Narrative:
Als Laboringenieur moechte ich Messreihen starten können, 
um Messungen zu einer Messreihe zu erhalten. 
					 
Scenario: Starten einer Messreihe ohne Messungen
Given Eine Messreihe ohne Messungen
When Es werden <anzahlMessungen> Messungen zur leeren Messreihe hinzugefuegt
Then Sind <anzahlMessungen> Messungen in der Messreihe enthalten

Examples:
|anzahlMessungen|
|0|
|1|
|2|
					 
Scenario: Starten einer Messreihe mit Messungen
Given Eine Messreihe mit Messungen
When Es wird 1 Messung zur gefuellten Messreihe hinzugefuegt
Then Ueberpruefen ob eine Exception geworfen wurde