# על הפלאגין
RedAlert הוא סוג של sideproject שלי.<br>
עקב המצב רציתי לחפש פלאגין שיתריע לנו כשיש התראות במקום לזרוק מבט כל פעם למסך השני (לאלו מאיתנו שיכולים לדחוף עוד מסך) או לטלפון, והאופציות שמצאתי לא היו דינמיות מספיק בשבילי מסיבות כאלו ואחרות.
<br><br>
אז בניתי אחד 👍

# אזהרה
שימו לב שהלפאגין אינו מהווה תחליף להתרעות פקע"ר, לאפליקציית פקע"ר ולא ניתן להסתמך עליו במאה אחוז! השארו קשובים לסביבה ולהתראות פקע"ר!

# תמיכה
הפלאגין חינם לגמרי ללא פרסומים, אבל כמובן שלא אתנגד לתשלום על העבודה אם אתם מוכנים להשקיע מעט :)

[![DonationButton]][DonationLink]

# שימוש והגדרות

## Permissions
לפלאגין יש פרימשן אחת: redalert.admin

## config.yml
קובץ הקונפיגורציה של השרת <br>

### ערכים
|     ערך      |   סוג    | חובה |                                        הסבר                                        |
|:------------:|:--------:|:----:|:----------------------------------------------------------------------------------:|
|    debug     | boolean  |  V   |  בדיבאג מוד אפשר להכניס ערך לdebug_source, וכך בעצם להשתמש בקובץ התראות של המפתח   |
| debug_source |  string  |  V   | כאשר הפלאגין נמצא בדיבאג מוד, ימשוך את ההתראות מהקישור הזה. (חובה שיהיה קובץ json) |
|   interval   | integer  |  V   |                     כל כמה זמן הפלאגין יבדוק אם יש התראה חדשה                      |
|  notifiers   | YamlTree |  V   |                             עבור אל [התראות](#התראות)                              |

### התראות
ערך זה מכיל את סוגי התראות והגדרה למה הוא עושה בסוג ההתראה הספציפי
<br>(לסוגי התראות הרץ את הפקודה /redalert listalerts)
<br><bold>ערך DEFAULT הוא חובה!</bold>

דוגמא:
```yaml
notifiers:
  DEFAULT:
    chat:
      format: |-
        <color:#FF0000> ====== <color:#AA0000><bold><underlined>צבע אדום</underlined></bold><color:#FF0000> ======
        <color:#AA0000><cities_he>
        <color:#FFA500><desc>
    
    sound:
      sound_key: minecraft:entity.evoker.prepare_wololo
      source: MASTER
      volume: 2.0
      pitch: 1.0

  UAV:
    chat:
      format: |-
        <blue> ====== <yellow><underlined>חדירת כלי טיס</underlined><blue> ======
        <color:#AA0000><cities_he>
        <color:#FFA500><desc>
```

במקרה הזה, בכל מקרה שיש התראה יפעלו כל ההתראות תחת DEFAULT, אך במקרה של חדירת כלי טיס עוין (UAV) יופעל רק התראה בצ'אט

ראה [סוגי התראות](#סוגי התראות)


## סוגי התראות

### צ'אט
|  ערך   |  סוג   |     הסבר     |
|:------:|:------:|:------------:|
| format | string | פורמט ההודעה |

דוגמא:
```yaml
    chat:
      format: |-
        <color:#FF0000> ====== <color:#AA0000><bold><underlined>צבע אדום</underlined></bold><color:#FF0000> ======
        <color:#AA0000><cities_he>
        <color:#FFA500><desc>
```

### קונסולה
|  ערך   |  סוג   |     הסבר     |
|:------:|:------:|:------------:|
| format | string | פורמט ההודעה |

דוגמא:
```yaml
    console:
      format: |-
        <color:#FF0000> ====== <color:#AA0000><bold><underlined>צבע אדום</underlined></bold><color:#FF0000> ======
        <color:#AA0000><cities_he>
        <color:#FFA500><desc>
```

הערה: הצ'אט שולח לכל הצ'אט אז גם הקונסולה יכולה לראות את ההודעה

### צליל
|    ערך    |  סוג   |                            הסבר                            |
|:---------:|:------:|:----------------------------------------------------------:|
| sound_key | string | שם הצליל במיינקראפט (אותם צלילים שאפשר לנגן עם /playsound) |
|  source   | string |      מקור הצליל (אותם מקורות שאפשריים עם /playsound)       |
|  volume   | double |            ווליום של ההתראה (נע בין 0.0 - 2.0)             |
|   pitch   | double |               גובה הצליל (נע בין 0.0 - 2.0)                |

דוגמא:
```yaml
    sound:
      sound_key: minecraft:entity.evoker.prepare_wololo
      source: MASTER
      volume: 2.0
      pitch: 1.0
```

### בוס בר
|   ערך    |   סוג   |                            הסבר                            |
|:--------:|:-------:|:----------------------------------------------------------:|
|  format  | string  |                פורמט של השם שיופיע בBossBar                |
|  color   | string  | צבע של הבר (PINK, BLUE, RED, GREEN, YELLOW, PURPLE, WHITE) |
| duration | integer |               הזמן בשניות שהבר ישאר על המסך                |

דוגמא:
```yaml
      format: <red><cities_he>
      color: RED
      duration: 15
```


### דוגמא לקובץ config.yml מלא
```yaml
debug: false
debug_source: http://localhost:8000/alerts.json
interval: 1

notifiers:
  DEFAULT:
    chat:
      format: |-
        <color:#FF0000> ====== <color:#AA0000><bold><underlined><hover:show_text:'Fichtl\'s Lied'><click:open_url:'https://youtu.be/1T14eOUf-28&t=12'>צבע אדום</click></hover></underlined></bold><color:#FF0000> ======
        <color:#AA0000><cities_he>
        <color:#FFA500><desc>
      disable: false

    console:
      format: |-
        <color:#FF0000> ====== <color:#AA0000><bold><underlined>Tseva Adom</underlined></bold><color:#FF0000> ======
        <color:#AA0000><bold><cities_en>
      disable: false

    sound:
      sound_key: minecraft:entity.evoker.prepare_wololo
      source: MASTER
      volume: 2.0
      pitch: 1.0
      disable: false

    bossbar:
      format: <red><cities_he>
      color: RED
      duration: 15
      disable: false

  UAV:
    chat:
      format: |-
        <blue> ====== <red>חדירת כלי טיס עוין <blue> =====
        <color:#40E0D0><desc>
        
    bossbar:
      format: <red><cities_he>
      color: BLUE
      duration: 20
```

[DonationButton]: https://img.shields.io/badge/Pay%20me%20a%20salary-red?style=flat&logo=Paypal
[DonationLink]: https://www.paypal.com/donate/?hosted_button_id=GDXSJCXNYFH7S (Paypal Donation)