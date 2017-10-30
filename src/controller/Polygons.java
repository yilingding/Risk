package controller;
import java.awt.Polygon;
/**
* This class listen to user's mouse click to select country.
*/

/**
* This is the class to build polygons for each country.
*/

public class Polygons {

/**
 * North America
 */
static int[] alaskaX = { 97, 96, 88, 53, 25, 11, 54, 87 };
static int[] alaskaY = { 186-25, 143-25, 98-25, 85-25, 96-25, 133-25, 158-25, 168-25 };
public static Polygon alaska = new Polygon(alaskaX, alaskaY, 8);

static int[] nwtX = { 210, 254, 236, 201, 93, 97 };
static int[] nwtY = { 149-25, 107-25, 69-25, 95-25, 97-25, 142-25 };
public static Polygon nwt = new Polygon(nwtX, nwtY, 6);

static int[] greenlandX = { 328, 341, 388, 403, 346, 280, 267, 308, 311 };
static int[] greenlandY = { 167-25, 134-25, 107-25, 42-25, 32-25, 63-25, 86-25, 117-25, 154-25 };
public static Polygon greenland = new Polygon(greenlandX, greenlandY, 9);

static int[] albertaX = { 98, 109, 175, 177, 101, 108 };
static int[] albertaY = { 188-25, 210-25, 211-25, 148-25, 147-25, 172-25 };
public static Polygon alberta = new Polygon(albertaX, albertaY, 6);

static int[] ontarioX = { 184, 181, 210, 237, 237, 235, 208 };
static int[] ontarioY = { 150-25, 210-25, 211-25, 230-25, 201-25, 183-25, 152-25 };
public static Polygon ontario = new Polygon(ontarioX, ontarioY, 7);

static int[] quebecX = { 245, 243, 267, 278, 283, 298, 289, 321, 294, 246 };
static int[] quebecY = { 202-25, 226-25, 232-25, 223-25, 250-25, 231-25, 214-25, 196-25, 154-25, 179-25 };
public static Polygon quebec = new Polygon(quebecX, quebecY, 10);

static int[] wusX = { 101, 100, 160, 179, 196, 193, 110 };
static int[] wusY = { 220-25, 282-25, 312-25, 267-25, 262-25, 216-25, 217-25 };
public static Polygon wus = new Polygon(wusX, wusY, 7);

static int[] eusX = { 164, 181, 197, 198, 258, 278, 279, 241, 229, 171 };
static int[] eusY = { 311-25, 271-25, 264-25, 217-25, 238-25, 228-25, 246-25, 324-25, 335-25, 321-25 };
public static Polygon eus = new Polygon(eusX, eusY, 10);

static int[] caX = { 112, 127, 184, 195, 193, 167, 159, 140 };
static int[] caY = { 296-25, 345-25, 405-25, 389-25, 340-25, 326-25, 315-25, 306-25 };
public static Polygon ca = new Polygon(caX, caY, 8);

/**
 * South America
 */
static int[] venezuelaX = { 188, 183, 218, 221, 229, 237, 242, 242, 248, 258, 263, 285, 299, 258, 214, 187 };
static int[] venezuelaY = { 404-25, 422-25, 440-25, 429-25, 423-25, 426-25, 424-25, 413-25, 416-25, 412-25, 421-25, 421-25, 408-25, 388-25, 377-25, 403-25 };
public static Polygon venezuela = new Polygon(venezuelaX, venezuelaY, 16);

static int[] peruX = { 179, 209, 198, 217, 237, 261, 276, 289, 275, 277, 243, 236, 191, 168, 180 };
static int[] peruY = { 425-25, 442-25, 456-25, 479-25, 476-25, 490-25, 521-25, 539-25, 545-25, 534-25, 519-25, 524-25, 488-25, 460-25, 425-25 };
public static Polygon peru = new Polygon(peruX, peruY, 15);

static int[] brazilX = { 301, 281, 261, 258, 247, 244, 225, 223, 205, 220, 219, 236, 259, 277, 277, 285, 290, 284,
		299, 313, 326, 339, 351, 370, 335, 315, 302 };
static int[] brazilY = { 412-25, 426-25, 425-25, 417-25, 419-25, 427-25, 427-25, 442-25, 455-25, 465-25, 474-25, 471-25, 484-25, 510-25, 520-25, 525-25, 541-25, 553-25,
		572-25, 539-25, 527-25, 527-25, 483-25, 460-25, 440-25, 431-25, 414-25 };
public static Polygon brazil = new Polygon(brazilX, brazilY, 27);

static int[] argentinaX = { 220, 219, 215, 220, 245, 262, 258, 258, 287, 299, 284, 289, 275, 271, 242, 238, 221 };
static int[] argentinaY = { 517-25, 563-25, 595-25, 646-25, 687-25, 684-25, 650-25, 620-25, 598-25, 579-25, 559-25, 545-25, 550-25, 537-25, 524-25, 529-25, 517-25 };
public static Polygon argentina = new Polygon(argentinaX, argentinaY, 17);

/**
 * Europe
 */
static int[] icelandX = { 394, 401, 424, 450, 454, 415, 400 };
static int[] icelandY = { 148-25, 168-25, 176-25, 161-25, 145-25, 142-25, 141-25 };
public static Polygon iceland = new Polygon(icelandX, icelandY, 7);

static int[] gbrX = { 409, 413, 421, 421, 443, 439, 441, 440, 415, 408, 402, 389, 395, 384, 365, 352, 367, 367, 382,
		395, 409, 422 };
static int[] gbrY = { 181-25, 193-25, 193-25, 211-25, 242-25, 254-25, 258-25, 263-25, 268-25, 208-25, 273-25, 276-25, 245-25, 271-25, 272-25, 256-25, 251-25, 233-25, 211-25,
		186-25, 180-25, 196-25 };
public static Polygon gbr = new Polygon(gbrX, gbrY, 22);

static int[] weuX = { 404, 452, 466, 460, 468, 454, 445, 430, 415, 395, 387, 385, 395, 415, 401 };
static int[] weuY = { 285-25, 266-25, 289-25, 306-25, 325-25, 356-25, 373-25, 382-25, 376-25, 374-25, 365-25, 323-25, 316-25, 318-25, 286-25 };
public static Polygon weu = new Polygon(weuX, weuY, 15);

static int[] seuX = { 465, 459, 467, 468, 483, 481, 466, 473, 489, 500, 505, 519, 534, 537, 528, 535, 546, 554, 548,
		545, 532, 522, 524, 509, 507, 509, 492, 488, 476, 466 };
static int[] seuY = { 289-25, 306-25, 323-25, 333-25, 347-25, 354-25, 355-25, 363-25, 362-25, 348-25, 347-25, 368-25, 368-25, 357-25, 345-25, 343-25, 318-25, 304-25, 301-25,
		280-25, 266-25, 280-25, 291-25, 292-25, 284-25, 277-25, 276-25, 286-25, 289-25, 289-25 };
public static Polygon seu = new Polygon(seuX, seuY, 30);

static int[] neuX = { 451, 454, 471, 479, 477, 483, 491, 490, 517, 527, 538, 542, 546, 539, 531, 530, 522, 523, 509,
		510, 492, 490, 465 };
static int[] neuY = { 266-25, 250-25, 232-25, 212-25, 206-25, 197-25, 206-25, 215-25, 209-25, 212-25, 221-25, 236-25, 240-25, 258-25, 259-25, 266-25, 280-25, 291-25, 291-25,
		278-25, 276-25, 286-25, 288-25 };
public static Polygon neu = new Polygon(neuX, neuY, 23);

static int[] ukraineX = { 545, 554, 548, 552, 588, 627, 658, 670, 671, 664, 666, 667, 663, 673, 669, 672, 657, 624,
		618, 629, 620, 635, 631, 608, 582, 589, 573, 575, 558, 554, 547, 545, 536, 529, 531, 540, 547, 538, 526,
		524, 529, 530 };
static int[] ukraineY = { 172-25, 165-25, 116-25, 104-25, 108-25, 102-25, 100-25, 110-25, 124-25, 134-25, 158-25, 174-25, 185-25, 192-25, 199-25, 214-25, 223-25, 228-25,
		250-25, 271-25, 280-25, 306-25, 320-25, 326-25, 302-25, 289-25, 292-25, 304-25, 293-25, 303-25, 301-25, 280-25, 268-25, 267-25, 261-25, 258-25, 240-25, 220-25, 211-25,
		201-25, 189-25, 179-25 };
public static Polygon ukraine = new Polygon(ukraineX, ukraineY, 42);

static int[] scandinaviaX = { 468, 462, 493, 507, 542, 552, 550, 553, 545, 526, 525, 519, 509, 511, 500, 486, 475 };
static int[] scandinaviaY = { 187-25, 157-25, 123-25, 103-25, 98-25, 103-25, 156-25, 162-25, 170-25, 174-25, 149-25, 177-25, 187-25, 199-25, 208-25, 188-25, 194-25 };
public static Polygon scandinavia = new Polygon(scandinaviaX, scandinaviaY, 17);

/**
 * Africa
 */
static int[] madagascarX = { 660, 659, 636, 617, 622, 629, 645 };
static int[] madagascarY = { 583-25, 615-25, 667-25, 665-25, 638-25, 610-25, 603-25 };
public static Polygon madagascar = new Polygon(madagascarX, madagascarY, 7);

static int[] safX = { 605, 609, 582, 583, 551, 520, 497, 503, 532, 564, 568, 584, 588 };
static int[] safY = { 577-25, 607-25, 626-25, 635-25, 684-25, 687-25, 606-25, 557-25, 564-25, 587-25, 568-25, 601-25, 584-25 };
public static Polygon saf = new Polygon(safX, safY, 13);

static int[] congoX = { 502, 490, 493, 520, 522, 540, 556, 586, 569, 563, 565, 546, 540, 534, 523, 513 };
static int[] congoY = { 556-25, 541-25, 531-25, 524-25, 502-25, 487-25, 515-25, 521-25, 566-25, 572-25, 586-25, 583-25, 579-25, 565-25, 565-25, 558-25 };
public static Polygon congo = new Polygon(congoX, congoY, 16);

static int[] eafX = { 590, 541, 541, 556, 587, 569, 583, 593, 605, 602, 644, 650, 621, 620, 596, 597 };
static int[] eafY = { 439-25, 454-25, 488-25, 513-25, 524-25, 568-25, 603-25, 581-25, 577-25, 561-25, 513-25, 489-25, 493-25, 483-25, 458-25, 446-25 };
public static Polygon eaf = new Polygon(eafX, eafY, 16);

static int[] egyptX = { 590, 542, 510, 493, 505, 537, 573, 582 };
static int[] egyptY = { 440-25, 454-25, 437-25, 405-25, 389-25, 392-25, 398-25, 412-25 };
public static Polygon egypt = new Polygon(egyptX, egyptY, 8);

static int[] nafX = { 499, 541, 542, 521, 520, 493, 493, 424, 393, 395, 422, 450, 480, 510, 508 };
static int[] nafY = { 421-25, 453-25, 489-25, 505-25, 524-25, 530-25, 520-25, 512-25, 462-25, 430-25, 382-25, 370-25, 369-25, 374-25, 389-25 };
public static Polygon naf = new Polygon(nafX, nafY, 15);

/**
 * Asia
 */
static int[] mdeX = { 574, 621, 640, 666, 683, 670, 692, 678, 652, 631, 607, 581, 546, 535, 540, 566, 582 };
static int[] mdeY = { 399-25, 470-25, 471-25, 456-25, 422-25, 404-25, 386-25, 334-25, 321-25, 321-25, 327-25, 320-25, 319-25, 345-25, 359-25, 365-25, 367-25 };
public static Polygon mde = new Polygon(mdeX, mdeY, 17);

static int[] afghX = { 653, 617, 623, 673, 692, 731, 731, 714, 723, 711, 700, 678, 659 };
static int[] afghY = { 324-25, 251-25, 229-25, 213-25, 237-25, 258-25, 277-25, 295-25, 310-25, 322-25, 315-25, 334-25, 321-25 };
public static Polygon afgh = new Polygon(afghX, afghY, 13);

static int[] uralX = { 664, 670, 656, 679, 691, 698, 712, 726, 724, 730, 724, 747, 746, 731, 721, 700, 672 };
static int[] uralY = { 135-25, 109-25, 72-25, 68-25, 110-25, 107-25, 134-25, 161-25, 171-25, 186-25, 202-25, 223-25, 233-25, 257-25, 246-25, 244-25, 213-25 };
public static Polygon ural = new Polygon(uralX, uralY, 17);

static int[] siberiaX = { 704, 691, 697, 723, 759, 777, 801, 803, 781, 799, 794, 783, 764, 763, 776, 774, 759, 745,
		723, 726 };
static int[] siberiaY = { 121-25, 102-25, 82-25, 45-25, 38-25, 47-25, 66-25, 102-25, 110-25, 150-25, 188-25, 178-25, 195-25, 218-25, 227-25, 258-25, 239-25, 235-25, 199-25,
		164-25 };
public static Polygon siberia = new Polygon(siberiaX, siberiaY, 20);

static int[] yakutskX = { 801, 803, 781, 800, 838, 856, 873, 877, 868, 869, 884, 853, 829, 813 };
static int[] yakutskY = { 66-25, 101-25, 110-25, 151-25, 138-25, 113-25, 120-25, 107-25, 99-25, 89-25, 71-25, 54-25, 72-25, 52-25 };
public static Polygon yakutsk = new Polygon(yakutskX, yakutskY, 14);

static int[] kamchatkaX = { 888, 939, 983, 947, 932, 915, 920, 897, 875, 875, 887, 897, 889, 870, 875, 864, 851,
		838, 838, 846, 857, 875, 876, 868, 868 };
static int[] kamchatkaY = { 69-25, 71-25, 101-25, 141-25, 197-25, 169-25, 150-25, 151-25, 153-25, 171-25, 173-25, 202-25, 248-25, 234-25, 197-25, 185-25, 189-25, 166-25,
		138-25, 120-25, 113-25, 120-25, 108-25, 100-25, 89-25 };
public static Polygon kamchatka = new Polygon(kamchatkaX, kamchatkaY, 25);

static int[] japanX = { 922, 958, 949, 954, 908, 893, 927 };
static int[] japanY = { 198-25, 206-25, 220-25, 268-25, 318-25, 304-25, 250-25 };
public static Polygon japan = new Polygon(japanX, japanY, 7);

static int[] irkutskX = { 838, 838, 853, 862, 875, 875, 848, 836, 830, 774, 762, 770, 764, 782, 794, 798, 811 };
static int[] irkutskY = { 138-25, 167-25, 189-25, 184-25, 200-25, 224-25, 199-25, 204-25, 224-25, 225-25, 217-25, 206-25, 192-25, 176-25, 185-25, 154-25, 138-25 };
public static Polygon irkutsk = new Polygon(irkutskX, irkutskY, 17);

static int[] mongoliaX = { 888, 873, 850, 835, 829, 773, 774, 800, 869, 891, 896, 880 };
static int[] mongoliaY = { 248-25, 223-25, 198-25, 202-25, 224-25, 222-25, 225-25, 288-25, 300-25, 297-25, 281-25, 264-25 };
public static Polygon mongolia = new Polygon(mongoliaX, mongoliaY, 12);

static int[] chinaX = { 867, 884, 875, 851, 831, 817, 801, 794, 788, 735, 739, 721, 712, 730, 729, 743, 759, 775,
		798, 845 };
static int[] chinaY = { 299-25, 335-25, 366-25, 390-25, 371-25, 384-25, 364-25, 369-25, 358-25, 340-25, 314-25, 311-25, 295-25, 274-25, 262-25, 234-25, 237-25, 256-25,
		286-25, 294-25 };
public static Polygon china = new Polygon(chinaX, chinaY, 20);

static int[] slamX = { 843, 857, 842, 835, 824, 812, 811, 795, 778, 794, 801, 819, 830 };
static int[] slamY = { 384-25, 424-25, 459-25, 468-25, 467-25, 452-25, 428-25, 421-25, 393-25, 366-25, 363-25, 383-25, 372-25 };
public static Polygon slam = new Polygon(slamX, slamY, 13);

static int[] indiaX = { 779, 745, 691, 678, 700, 710, 724, 738, 733, 793 };
static int[] indiaY = { 391-25, 487-25, 386-25, 335-25, 314-25, 321-25, 309-25, 317-25, 341-25, 366-25 };
public static Polygon india = new Polygon(indiaX, indiaY, 10);

/**
 * Australia
 */
static int[] indonesiaX = { 777, 816, 829, 863, 875, 884, 854, 777 };
static int[] indonesiaY = { 495-25, 560-25, 568-25, 567-25, 546-25, 512-25, 481-25, 494-25 };
public static Polygon indonesia = new Polygon(indonesiaX, indonesiaY, 8);

static int[] ngX = { 880, 890, 905, 959, 962, 947, 913, 886 };
static int[] ngY = { 475-25, 493-25, 515-25, 529-25, 522-25, 485-25, 466-25, 465-25 };
public static Polygon ng = new Polygon(ngX, ngY, 8);

static int[] wausX = { 827, 843, 873, 937, 942, 902, 896, 881, 827 };
static int[] wausY = { 611-25, 674-25, 669-25, 670-25, 616-25, 613-25, 554-25, 559-25, 608-25 };
public static Polygon waus = new Polygon(wausX, wausY, 9);

static int[] eausX = { 902, 904, 945, 942, 953, 981, 999, 985, 954, 901 };
static int[] eausY = { 552-25, 607-25, 606-25, 672-25, 687-25, 685-25, 644-25, 594-25, 539-25, 551-25 };
public static Polygon eaus = new Polygon(eausX, eausY, 10);
}