import java.applet.Applet;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.awt.image.DataBuffer;
import java.awt.image.DataBufferByte;
import java.awt.image.Raster;
import java.awt.image.SampleModel;
import java.awt.image.SinglePixelPackedSampleModel;
import java.awt.image.WritableRaster;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.ClipboardOwner;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.StringSelection;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.io.ByteArrayInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Comparator;

import javax.vecmath.Vector2d;
import javax.vecmath.Vector2f;

public class htsOcr extends Applet implements MouseListener,
		MouseMotionListener,KeyListener, ClipboardOwner {

	private static final long serialVersionUID = 1L;

	public int[] drawData = null;

	public int mouse_x = 0;

	public int mouse_y = 0;

	public static int avg_x = 0;
	public static int avg_y = 0;
	public static int SpiralAlignX = 4;
	public static int SpiralAlignY = 1;//4 default

	public boolean POLLGraphics = false;
	public boolean CLICKED = false;
	public boolean SpexsAdded = false;
	public boolean CopiedToClip = false;
	public boolean Clippy = false;
	
	public static int CropSize = 36;
	public static int CropAlignX = 15;
	public static int CropAlignY = 10;
	

	public org.apache.pivot.collections.ArrayList<Character> Characters = new org.apache.pivot.collections.ArrayList<Character>();
	public org.apache.pivot.collections.ArrayList<Spex> Spexs = new org.apache.pivot.collections.ArrayList<Spex>();

	public void setDefaultDrawData() {
		this.drawData = new int[] { 838, 428, 824, 431, 439, 411, 4, -310, 180,
				612, 396, 607, 393, 245, 442, 247, 434, 569, 678, 560, 679,
				384, 363, 380, 367, 597, 661, 592, 663, 478, 633, 4, -200, 359,
				640, 585, 645, 578, 323, 603, 337, 600, 362, 194, 358, 196,
				291, 449, 297, 450, 578, 359, 583, 346, 580, 403, 4, -30, 359,
				311, 232, 317, 227, 416, 397, 421, 391, 414, 451, 415, 447,
				567, 390, 4, 160, 359, 342, 521, 4, -80, 180, 531, 313, 4,
				-180, 359, 350, 336, 4, -310, 359, 483, 761, 491, 748, 635,
				548, 647, 555, 467, 571, 463, 568, 698, 629, 7, -75, 255, 628,
				437, 624, 429, 583, 579, 4, 75, 230, 627, 603, 4, -230, 180,
				646, 351, 3, -130, 225, 714, 456, 710, 454, 615, 583, 617, 593,
				481, 614, 484, 608, 623, 379, 4, -40, 359, 548, 392, 4, 170,
				359, 572, 238, 579, 239, 195, 549, 202, 550, 572, 238, 579,
				239, 595, 600, 599, 598, 674, 608, 4, -310, 359, 459, 363, 4,
				-150, 359, 490, 703, 496, 704, 405, 641, 408, 643, 432, 399,
				427, 406, 687, 398, 4, -60, 180, 696, 542, 708, 552, 441, 744,
				436, 742, 409, 162, 415, 160, 653, 532, 4, -290, 359, 783, 447,
				782, 440, 655, 466, 655, 463, 555, 601, 553, 588, 506, 368,
				507, 375, 497, 328, 501, 327, 741, 450, 7, -170, 180, 514, 611,
				511, 625, 481, 351, 4, -160, 359, 597, 654, 597, 669, 327, 370,
				4, -75, 230, 760, 620, 3, -210, 225, 794, 339, 792, 334, 265,
				581, 278, 585, 484, 362, 485, 352, 302, 405, 315, 409, 525,
				395, 525, 381, 676, 514, 4, -280, 180, 530, 381, 525, 381, 442,
				568, 439, 564, 642, 420, 638, 423, 202, 494, 188, 488, 453,
				559, 450, 555, 396, 509, 397, 514, 606, 363, 600, 360, 424,
				787, 417, 793, 389, 406, 4, -120, 270, 754, 617, 4, -255, 230,
				476, 150, 467, 151, 431, 613, 427, 609, 384, 306, 378, 312,
				670, 649, 666, 653, 381, 232, 383, 239, 201, 543, 202, 550,
				601, 293, 7, -110, 180, 422, 450, 7, -20, 180, 628, 598, 625,
				601, 839, 432, 838, 428, 531, 602, 3, 90, 225, 427, 406, 416,
				397, 753, 295, 755, 298, 326, 610, 323, 603, 564, 277, 561,
				291, 555, 339, 553, 346, 423, 364, 418, 359, 713, 488, 4, -90,
				180, 527, 375, 535, 375, 635, 210, 630, 208, 537, 638, 531,
				652, 840, 487, 840, 488, 568, 282, 4, -100, 180, 702, 517, 709,
				522, 638, 507, 4, -100, 180, 572, 603, 577, 602, 495, 314, 499,
				313, 405, 336, 4, -50, 180, 305, 528, 4, -260, 359, 683, 404,
				689, 401, 595, 385, 589, 381, 774, 692, 3, -220, 225, 615, 258,
				4, -200, 359, 642, 420, 646, 426, 383, 231, 390, 244, 261, 390,
				3, -20, 359, 402, 384, 398, 375, 529, 338, 528, 345, 332, 605,
				329, 601, 334, 452, 4, -10, 180, 470, 812, 478, 814, 579, 348,
				583, 346, 731, 659, 727, 651, 462, 363, 463, 373, 771, 576, 4,
				-200, 180, 738, 735, 738, 730, 744, 522, 743, 529, 250, 491,
				243, 490, 480, 800, 478, 814, 623, 406, 629, 403, 634, 448,
				627, 449, 686, 356, 683, 353, 689, 762, 693, 760, 401, 442, 7,
				-20, 180, 418, 469, 7, -10, 180, 604, 576, 595, 565, 504, 640,
				4, -10, 359, 505, 668, 501, 667, 490, 610, 488, 617, 587, 585,
				3, 120, 225, 710, 389, 706, 382, 694, 298, 689, 294, 628, 598,
				637, 608, 677, 662, 677, 657, 699, 682, 692, 687, 657, 559,
				662, 567, 629, 527, 631, 521, 399, 465, 4, -280, 359, 601, 396,
				614, 389, 384, 407, 386, 404, 449, 624, 454, 627, 451, 259,
				444, 261, 428, 576, 427, 575, 733, 447, 734, 451, 217, 599,
				219, 603, 726, 256, 4, -220, 359, 648, 443, 653, 441, 192, 428,
				195, 432, 654, 263, 647, 276, 480, 636, 473, 643, 613, 623,
				609, 617, 285, 629, 287, 633, 588, 810, 585, 796, 386, 542,
				387, 546, 394, 440, 393, 444, 418, 548, 422, 556, 443, 649,
				436, 661, 622, 455, 635, 450, 430, 572, 4, -5, 230, 688, 489,
				688, 484, 589, 413, 598, 402, 566, 664, 569, 678, 726, 324, 4,
				-50, 180, 651, 467, 4, -260, 270, 481, 753, 487, 754, 265, 260,
				261, 265, 321, 367, 3, -30, 225, 821, 383, 3, -160, 225, 407,
				444, 406, 448, 347, 638, 351, 642, 627, 509, 628, 501, 328,
				488, 331, 491, 344, 420, 343, 421, 415, 692, 418, 694, 497,
				294, 494, 280, 553, 609, 551, 624, 574, 595, 566, 598, 604,
				407, 598, 402, 697, 347, 694, 344, 678, 233, 4, -210, 359, 534,
				668, 528, 668, 561, 291, 566, 292, 506, 617, 512, 618, 328,
				488, 342, 488, 673, 537, 679, 543, 531, 365, 4, 0, 180, 685,
				395, 679, 398, 607, 424, 4, 130, 359, 703, 484, 703, 492, 584,
				330, 595, 324, 561, 648, 562, 641, 239, 319, 4, -30, 180, 416,
				590, 411, 584, 252, 537, 3, -350, 225, 771, 404, 769, 398, 302,
				221, 311, 232, 829, 597, 827, 593, 624, 542, 626, 539, 374,
				551, 387, 546, 236, 653, 240, 661, 513, 367, 514, 374, 668,
				325, 3, -130, 225, 595, 377, 588, 389, 818, 384, 818, 382, 530,
				187, 525, 187, 443, 593, 4, -220, 180, 498, 681, 503, 681, 387,
				488, 401, 488, 612, 420, 3, 220, 359, 530, 381, 525, 381, 287,
				347, 4, -300, 359, 835, 488, 4, -225, 230, 341, 454, 4, -10,
				180, 352, 387, 357, 392, 752, 357, 756, 358, 409, 162, 408,
				155, 446, 268, 454, 265, 443, 649, 436, 645, 358, 588, 373,
				586, 641, 680, 7, -95, 255, 824, 431, 825, 435, 662, 436, 675,
				431, 752, 488, 738, 488, 537, 290, 531, 276, 792, 488, 3, -180,
				359, 525, 638, 531, 652, 654, 263, 658, 266, 576, 744, 579,
				758, 209, 545, 195, 547, 527, 816, 534, 816, 573, 707, 573,
				714, 425, 577, 3, -320, 225, 342, 491, 342, 485, 630, 372, 622,
				375, 412, 282, 4, -330, 359, 699, 455, 700, 461, 551, 624, 558,
				623, 586, 583, 587, 582, 366, 485, 374, 489, 783, 575, 781,
				580, 357, 634, 4, -50, 270, 267, 392, 4, -110, 359, 408, 643,
				417, 633, 657, 411, 660, 416, 647, 555, 645, 551, 210, 600,
				225, 600, 690, 765, 4, -240, 180, 670, 609, 667, 600, 403, 534,
				7, -195, 255, 575, 368, 571, 366, 479, 807, 473, 806, 431, 213,
				430, 214, 573, 607, 4, -250, 180, 411, 468, 410, 472, 424, 529,
				423, 527, 497, 328, 495, 314, 816, 592, 829, 597, 650, 390,
				641, 392, 531, 338, 531, 352, 789, 442, 776, 445, 581, 360,
				575, 358, 646, 792, 638, 795, 604, 571, 604, 576, 761, 293, 7,
				-140, 180, 682, 227, 3, -120, 359, 671, 440, 669, 433, 663,
				407, 651, 414, 246, 657, 243, 652, 361, 693, 352, 697, 188,
				488, 202, 482, 623, 737, 7, -250, 180, 464, 673, 469, 659, 292,
				442, 291, 449, 691, 352, 7, -140, 180, 530, 388, 7, 270, 180,
				738, 491, 738, 485, 606, 693, 4, -340, 180, 636, 546, 634, 550,
				578, 359, 583, 346, 615, 336, 620, 330, 345, 556, 3, -340, 359,
				807, 647, 792, 645, 534, 243, 528, 243, 204, 375, 217, 380,
				487, 206, 479, 193, 403, 342, 394, 331, 605, 701, 602, 695,
				528, 809, 532, 809, 471, 385, 470, 385, 473, 388, 4, -105, 230,
				593, 567, 598, 563, 677, 459, 684, 459, 727, 726, 731, 722,
				487, 609, 4, -20, 180, 527, 144, 527, 130, 436, 749, 446, 738,
				445, 342, 7, -60, 180, 343, 268, 339, 264, 656, 489, 7, -35,
				255, 245, 442, 259, 444, 363, 516, 372, 519, 528, 345, 535,
				345, 396, 467, 389, 462, 398, 726, 393, 731, 462, 576, 459,
				573, 294, 624, 4, -330, 180, 588, 810, 590, 806, 575, 368, 571,
				366, 691, 304, 684, 299, 352, 553, 4, -70, 359, 627, 548, 630,
				542, 403, 417, 407, 411, 607, 553, 610, 549, 402, 384, 398,
				375, 339, 264, 344, 260, 654, 513, 656, 506, 665, 444, 662,
				436, 419, 796, 4, -20, 359, 527, 763, 534, 763, 685, 461, 671,
				463, 438, 381, 434, 377, 583, 190, 576, 203, 601, 396, 614,
				389, 531, 595, 4, 45, 230, 438, 339, 442, 336, 456, 398, 7,
				-265, 255, 534, 283, 528, 283, 763, 286, 753, 295, 300, 488, 4,
				-90, 359, 598, 606, 603, 604, 579, 608, 575, 610, 530, 619, 7,
				-125, 255, 621, 540, 4, 60, 270, 442, 255, 449, 252, 329, 457,
				343, 459, 421, 528, 3, 20, 225, 634, 303, 639, 302, 463, 300,
				4, -160, 270, 573, 745, 579, 744, 769, 688, 4, -265, 230, 425,
				682, 422, 680, 814, 385, 4, -205, 230, 713, 492, 703, 484, 528,
				645, 534, 645, 608, 700, 4, -160, 180, 364, 488, 4, -90, 359,
				712, 520, 4, -280, 359, 623, 406, 627, 412, 626, 568, 4, -310,
				359, 343, 459, 344, 454, 728, 416, 4, -250, 270, 745, 367, 743,
				363, 431, 313, 7, -275, 255, 735, 561, 7, -200, 180, 710, 389,
				706, 382, 547, 603, 555, 601, 573, 745, 579, 744, 557, 325,
				562, 326, 392, 722, 398, 726, 470, 382, 3, -60, 225, 718, 263,
				727, 259, 506, 341, 504, 350, 449, 349, 445, 351, 329, 737,
				316, 744, 625, 214, 635, 203, 763, 286, 766, 289, 647, 276,
				651, 278, 445, 351, 438, 339, 537, 250, 531, 236, 302, 221,
				311, 232, 534, 679, 531, 682, 621, 339, 615, 336, 629, 312,
				634, 315, 233, 315, 4, -30, 180, 461, 296, 458, 297, 579, 348,
				583, 346, 525, 201, 525, 187, 489, 247, 7, -295, 255, 652, 632,
				3, -230, 359, 714, 456, 700, 458, 447, 413, 441, 408, 527, 130,
				535, 130, 195, 491, 195, 485, 637, 608, 634, 611, 422, 556,
				410, 563, 695, 428, 4, -205, 230, 267, 584, 4, -250, 180, 610,
				555, 4, 140, 180, 426, 507, 411, 505, 574, 595, 570, 582, 586,
				395, 579, 400, 625, 729, 621, 731, 630, 542, 626, 539, 456,
				576, 4, 50, 180, 684, 489, 4, -180, 180, 392, 570, 7, -330,
				180, 716, 328, 729, 327, 525, 638, 531, 652, 534, 315, 530,
				324, 677, 489, 4, -180, 180, 294, 528, 303, 531, 427, 428, 3,
				-30, 225, 377, 299, 378, 312, 511, 374, 4, -170, 180, 531, 668,
				531, 682, 842, 488, 3, -180, 225, 647, 626, 4, -320, 359, 687,
				759, 4, -240, 180, 390, 513, 405, 516, 675, 517, 682, 518, 400,
				570, 388, 577, 632, 314, 639, 302, 507, 351, 4, -350, 359, 625,
				729, 630, 742, 467, 571, 458, 582, 427, 618, 436, 608, 388,
				577, 386, 573, 692, 769, 696, 766, 242, 320, 240, 325, 510,
				367, 4, -350, 180, 341, 419, 3, -20, 225, 799, 333, 784, 335,
				727, 598, 722, 595, 358, 588, 367, 576, 466, 658, 471, 660,
				734, 659, 4, -310, 359, 465, 137, 467, 151, 572, 252, 579, 239,
				738, 416, 736, 410, 552, 366, 4, -10, 180, 566, 380, 571, 381,
				300, 626, 297, 622, 348, 421, 4, -65, 230, 731, 412, 736, 410,
				323, 666, 314, 668, 379, 545, 7, -340, 180, 330, 452, 329, 457,
				785, 488, 4, -270, 359, 799, 333, 790, 345, 547, 376, 556, 367,
				369, 459, 7, -225, 255, 598, 299, 594, 298, 583, 631, 4, -295,
				230, 370, 358, 380, 367, 527, 763, 531, 749, 417, 487, 7, -215,
				255, 647, 513, 649, 504, 278, 585, 275, 577, 571, 366, 566,
				380, 487, 609, 4, -20, 180, 776, 399, 765, 409, 611, 584, 4,
				-140, 359, 410, 563, 406, 555, 438, 381, 440, 379, 213, 607,
				210, 600, 531, 709, 4, -180, 359, 205, 433, 206, 428, 650, 588,
				4, -130, 180, 588, 795, 583, 796, 505, 668, 503, 681, 531, 365,
				4, 0, 180, 428, 360, 423, 354, 271, 269, 4, -130, 270, 354,
				637, 356, 639, 411, 505, 412, 512, 570, 582, 562, 585, 555,
				616, 551, 617, 446, 268, 442, 255, 635, 428, 4, -240, 270, 364,
				776, 371, 764, 205, 371, 204, 375, 429, 551, 442, 544, 276,
				707, 287, 698, 696, 766, 689, 754, 596, 592, 591, 594, 379,
				433, 4, -290, 359, 375, 363, 378, 359, 468, 670, 464, 673, 384,
				407, 380, 404, 670, 539, 4, -110, 359, 531, 599, 532, 599, 635,
				203, 635, 218, 716, 328, 721, 334, 448, 563, 445, 560, 446,
				582, 453, 588, 442, 568, 453, 559, 369, 775, 364, 776, 732,
				415, 731, 412, 300, 626, 287, 633, 414, 630, 417, 633, 832,
				541, 4, -100, 359, 287, 698, 281, 692, 802, 641, 800, 646, 582,
				615, 577, 602, 461, 609, 473, 600, 618, 562, 621, 558, 644,
				353, 643, 352, 354, 637, 356, 639, 388, 577, 386, 573, 353,
				700, 4, -40, 359, 302, 221, 308, 216, 761, 398, 776, 399, 429,
				429, 429, 430, 683, 353, 694, 344, 612, 436, 624, 429, 557,
				339, 555, 353, 702, 547, 700, 553, 669, 373, 3, -140, 359, 400,
				331, 4, -50, 180, 288, 627, 4, -330, 180, 500, 674, 7, -280,
				180, 323, 663, 4, -230, 359, 336, 458, 337, 453, 426, 396, 421,
				401, 293, 488, 3, 0, 359, 758, 619, 759, 618, 477, 201, 483,
				200, 513, 593, 4, -190, 359, 413, 553, 416, 559, 567, 289, 4,
				-100, 180, 643, 488, 629, 488, 627, 509, 628, 501, 475, 584, 7,
				-155, 255, 243, 488, 257, 488, 406, 593, 4, -230, 180, 360,
				385, 357, 392, 693, 288, 700, 294, 577, 246, 572, 245, 637,
				511, 628, 501, 743, 559, 730, 554, 586, 635, 585, 635, 748,
				448, 747, 444, 645, 578, 648, 591, 452, 705, 3, -290, 225, 631,
				609, 4, -230, 180, 817, 589, 815, 594, 436, 749, 435, 734, 645,
				447, 4, -250, 270, 416, 590, 403, 590, 321, 730, 316, 744, 637,
				527, 626, 516, 234, 321, 236, 317, 558, 666, 566, 664, 641,
				396, 4, -50, 359, 374, 551, 372, 547, 230, 313, 227, 318, 769,
				570, 768, 575, 559, 648, 557, 635, 386, 661, 4, -40, 359, 387,
				488, 391, 491, 576, 613, 4, -250, 180, 727, 598, 724, 603, 394,
				331, 398, 328, 448, 632, 450, 617, 675, 431, 678, 439, 427,
				456, 414, 451, 462, 363, 463, 373, 832, 434, 7, -170, 180, 637,
				527, 626, 516, 736, 524, 750, 527, 418, 686, 7, -300, 180, 717,
				595, 4, -300, 270, 676, 510, 683, 511, 674, 484, 688, 484, 358,
				588, 373, 586, 756, 358, 744, 365, 503, 647, 3, -280, 359, 456,
				576, 4, 50, 180, 406, 383, 4, -310, 359, 347, 337, 343, 329,
				421, 357, 4, -320, 180, 606, 619, 4, -330, 180, 629, 312, 634,
				315, 527, 816, 531, 802, 603, 286, 599, 285, 646, 351, 3, -130,
				225, 473, 143, 466, 144, 560, 325, 562, 311, 525, 290, 531,
				276, 633, 782, 641, 779, 374, 516, 4, -260, 359, 267, 267, 266,
				269, 684, 299, 693, 288, 442, 418, 437, 413, 205, 431, 192,
				428, 365, 587, 362, 582, 381, 666, 3, -310, 359, 474, 328, 7,
				-285, 255, 662, 507, 649, 504, 396, 509, 397, 514, 463, 594,
				461, 609, 312, 222, 306, 227, 369, 763, 374, 766, 499, 286,
				492, 288, 699, 694, 699, 682, 314, 412, 316, 407, 589, 413,
				595, 418, 663, 377, 4, -230, 359, 530, 395, 525, 395, 568, 671,
				561, 672, 597, 669, 587, 658, 622, 645, 4, -330, 359, 454, 578,
				458, 582, 639, 485, 643, 488, 460, 622, 448, 632, 685, 756,
				689, 754, 335, 522, 4, -260, 180, 380, 404, 383, 398, 567, 285,
				563, 284, 294, 630, 291, 626, 434, 377, 439, 373, 374, 301, 4,
				-320, 180, 729, 724, 738, 735, 442, 544, 437, 537, 454, 698, 4,
				-335, 230, 678, 466, 677, 459, 663, 717, 7, -95, 255, 485, 616,
				4, -200, 180, 461, 570, 4, 50, 180, 644, 785, 637, 788, 370,
				358, 373, 354, 302, 448, 4, -100, 270, 681, 484, 681, 489, 666,
				566, 4, -300, 359, 494, 714, 491, 717, 591, 322, 4, -20, 180,
				648, 443, 649, 445, 389, 236, 383, 239, 254, 537, 255, 538,
				533, 137, 527, 137, 471, 385, 470, 385, 491, 376, 7, -285, 255,
				629, 606, 633, 603, 314, 312, 304, 303, 447, 557, 4, 40, 180,
				663, 407, 667, 414, 453, 588, 440, 590, 549, 385, 3, 260, 359,
				266, 269, 261, 265, 660, 462, 655, 463, 480, 746, 483, 761,
				381, 361, 4, -40, 180, 569, 278, 564, 277, 425, 682, 418, 694,
				778, 579, 4, -200, 180, 530, 194, 7, -90, 180, 688, 580, 7,
				-65, 255, 610, 625, 4, -150, 180, 699, 427, 699, 426, 409, 635,
				7, -310, 180, 369, 428, 376, 435, 494, 589, 4, 25, 230, 782,
				440, 789, 440, 429, 657, 436, 661, 393, 444, 406, 448, 279,
				341, 284, 349, 702, 426, 3, -160, 225, 403, 342, 407, 339, 462,
				602, 467, 604, 430, 211, 3, -70, 225, 421, 613, 427, 618, 479,
				193, 476, 208, 324, 368, 323, 369, 457, 292, 458, 297, 742,
				563, 743, 559, 217, 380, 218, 376, 418, 505, 419, 509, 585,
				638, 3, -250, 225, 597, 366, 604, 354, 318, 737, 322, 741, 348,
				270, 4, -140, 270, 625, 651, 3, -240, 359, 492, 288, 492, 281,
				683, 515, 4, -100, 180, 585, 198, 579, 197, 425, 362, 4, -140,
				180, 212, 373, 7, -20, 180, 386, 614, 380, 618, 593, 597, 4,
				-240, 180, 751, 525, 744, 522, 708, 552, 693, 553, 568, 373,
				573, 375, 574, 371, 4, -110, 180, 572, 378, 4, -110, 180, 626,
				744, 630, 742, 668, 651, 677, 662, 553, 346, 559, 347, 386,
				614, 382, 609, 342, 518, 335, 519, 309, 307, 313, 302, 531,
				236, 525, 250, 355, 182, 351, 184, 612, 557, 615, 553, 632,
				573, 3, -220, 359, 358, 188, 354, 190, 259, 536, 4, -35, 230,
				276, 707, 271, 701, 433, 431, 4, -75, 230, 662, 507, 661, 515,
				444, 415, 4, -130, 180, 358, 196, 351, 184, 681, 401, 4, -240,
				180, 664, 330, 4, -175, 230, 356, 639, 351, 642, 656, 489, 7,
				-35, 255, 666, 327, 665, 326, 603, 620, 607, 626, 661, 469,
				660, 462, 728, 558, 730, 554, 634, 471, 7, -25, 255, 629, 485,
				629, 491, 579, 758, 581, 754, 427, 526, 4, -25, 230, 642, 356,
				4, -175, 230, 548, 596, 554, 594, 655, 447, 653, 441, 399, 337,
				402, 334, 615, 559, 4, 140, 180, 360, 191, 4, -60, 180, 410,
				154, 414, 168, 609, 403, 614, 389, 785, 533, 7, -45, 255, 454,
				703, 453, 702, 842, 544, 834, 539, 555, 642, 562, 641, 432,
				542, 435, 548, 702, 692, 4, -140, 180, 412, 416, 410, 420, 398,
				718, 391, 730, 621, 438, 618, 433, 674, 489, 674, 484, 315,
				566, 4, -25, 230, 617, 251, 3, -110, 359, 427, 456, 429, 452,
				654, 272, 7, -120, 180, 343, 268, 345, 266, 573, 707, 566, 708,
				408, 272, 408, 281, 297, 447, 297, 450, 389, 607, 378, 616,
				593, 376, 589, 381, 620, 414, 630, 405, 240, 661, 252, 654,
				473, 136, 465, 137, 582, 615, 577, 617, 433, 651, 439, 655,
				772, 690, 773, 689, 535, 365, 527, 375, 583, 579, 4, 75, 230,
				733, 447, 747, 444, 627, 449, 630, 456, 614, 343, 621, 331,
				599, 412, 593, 407, 774, 577, 776, 573, 376, 357, 4, -40, 180,
				721, 598, 722, 595, 565, 591, 572, 588, 621, 558, 610, 549,
				336, 526, 343, 525, 583, 190, 587, 205, 749, 485, 752, 488,
				493, 703, 491, 717, 783, 575, 769, 570, 584, 330, 592, 333,
				611, 699, 609, 692, 410, 472, 424, 475, 407, 411, 417, 422,
				532, 756, 528, 756, 365, 392, 353, 385, 424, 471, 424, 475,
				433, 217, 4, -115, 230, 309, 569, 3, -340, 225, 357, 185, 4,
				-60, 180, 312, 569, 311, 568, 597, 603, 4, -240, 180, 309, 307,
				313, 302, 240, 325, 227, 318, 400, 570, 398, 566, 569, 701,
				571, 715, 243, 488, 257, 488, 814, 385, 4, -205, 230, 622, 527,
				637, 527, 429, 551, 425, 544, 253, 437, 252, 443, 641, 779,
				646, 792, 511, 625, 503, 624, 403, 505, 390, 513, 492, 596, 3,
				70, 225, 650, 534, 644, 528, 457, 292, 462, 289, 493, 593, 494,
				594, 559, 314, 562, 311, 702, 426, 3, -160, 225, 500, 320, 7,
				-80, 180, 250, 484, 250, 491, 309, 296, 304, 303, 509, 603,
				515, 595, 555, 377, 547, 376, 717, 381, 4, -60, 180, 807, 647,
				798, 636, 277, 698, 281, 703, 534, 711, 530, 719, 603, 604,
				596, 592, 401, 485, 401, 491, 638, 423, 639, 426, 706, 382,
				719, 384, 611, 358, 604, 354, 530, 201, 525, 201, 443, 383, 4,
				-140, 270, 304, 408, 302, 405, 569, 393, 562, 399, 594, 298,
				599, 285, 442, 562, 4, 40, 180 };
	}

	public void init() { // set size, mouse listeners, set drawData
		this.setSize(new Dimension(1000, 950));
		this.setMinimumSize(new Dimension(1000, 950));

		addMouseListener(this);
		addMouseMotionListener(this);
		addKeyListener(this);

		String clipboard = this.getClipboardContents();

		if (clipboard != null) {
			String[] drawDataStr = clipboard.split(",");
			drawData = new int[drawDataStr.length];
			int i;
			for (i = 0; i < drawDataStr.length; i++) {

				try {
					drawData[i] = Integer.decode(drawDataStr[i]).intValue();
				} catch (java.lang.NumberFormatException e) {
					this.setDefaultDrawData();
				}
			}
			Clippy = true;
		} else {
			this.setDefaultDrawData();
		}
		this.initialPROCESSING();
	}

	public void stop() {
	}

	
	/*---------------_Testing---------------*/
	public int CURRENTTEST = 0;
	@Override
	public void mouseEntered(MouseEvent e) {
	}
	
	@Override
	public void keyPressed(KeyEvent e) {
		String key = String.valueOf(e.getKeyChar());
		
		
		if(key.equals(("d"))){
			CURRENTTEST++;
			if(CURRENTTEST >= this.Characters.getLength()){
				CURRENTTEST = 0;
			}
			this.repaint();
		}
		if(key.equals(("a"))){
			CURRENTTEST--;
			if(CURRENTTEST < 0){
				CURRENTTEST = this.Characters.getLength()-1;
			}
			this.repaint();
		}
		if(key.equals(("w"))){
			
			if(CURRENTTEST+36 < this.Characters.getLength()){
				CURRENTTEST+=36;
			}
			this.repaint();
		}
		if(key.equals(("s"))){
			
			if(CURRENTTEST-36 >= 0){
				CURRENTTEST-=36;
			}
			this.repaint();
		}
		e.consume();
		
	}
	/*---------------_Testing---------------*/
	
	
	
	
	
	
 
	public void paint(Graphics g) { //XXX create buffer?

		this.drawIt(g);

		if (!this.POLLGraphics && this.CLICKED) {

			int origin_x = 0;
			int origin_y = 0;

			origin_x = htsOcr.avg_x + SpiralAlignX;
			origin_y = htsOcr.avg_y + SpiralAlignY;

			int length = 400;
			for (int deg = -5; deg < 360; deg += 10) {
				g.drawLine(origin_x, origin_y, (int) (origin_x + length
						* Math.cos(Math.toRadians(deg))),
						(int) (origin_y + length
								* Math.sin(Math.toRadians(deg))));
			}
			
			
			
			if(CopiedToClip == false && Clippy == true){
				
				String codeString ="";
				
				for (int i = 0; i < this.Characters.getLength(); i++) {	
					
					int ls=0,as=0;
					for(int s = 0; s < this.Characters.get(i).CharSpex.getLength(); s++){
						if(this.Characters.get(i).CharSpex.get(s).getType() == Spex.PointType.LINE_START){
							ls++;
						}
						else if(this.Characters.get(i).CharSpex.get(s).getType() == Spex.PointType.ARC_START){
							as++;
							
						}
						
					}
					
					int CHARACTER = ls*10 + as;
					int PIXELS = this.Characters.get(i).getPixelCount();
					int TL = this.Characters.get(i).getSpexTL();
					int TR = this.Characters.get(i).getSpexTR();
					int BL = this.Characters.get(i).getSpexBL();
					int BR = this.Characters.get(i).getSpexBR();
					
					String letter = new String();
					
					String predicted = this.Characters.get(i).getSpexCharacter(); //XXX 
					
					
					switch(CHARACTER){	
						case 31: 
							letter = "5,D";
							if(PIXELS < 39) //39 may be D though
								letter = "5";
							else if(PIXELS > 39)
								letter = "D";
							else
								if(TL%100 > 2)
									letter = "5";
							break;
							
						case 11: 
							letter = "6,9";
							if(BR == 200 || BL == 200)
								letter = "6";
							if(TR == 200 || TL == 200)
								letter = "9";
							break;
							
						case 21: 
							if(PIXELS < 36) 
								letter = "2"; 
							else
								letter = "9"; 
							break;
							
							
						case 2: 
							letter = "8";
							break;
							
						case 22: 
							letter = "0";
							break;
							
						case 53:
						case 52:
						case 42: 
							letter = "B";
							break;
							
						case 12: 
							letter = "3";
							break;
					
						case 40: //E
						case 50: //E
							if(PIXELS <= 24 && CHARACTER == 40)
								letter = "1";
							else
								if(TL*TR*BL*BR == 0)
									letter = "4";
								else
									letter = "E";
							break;
							
					}
					
					String THESTRSING = predicted;
					if(THESTRSING.equals("")){
						THESTRSING = letter;
					}
					if(THESTRSING.equals("")){
						THESTRSING = "F";
					}
					codeString += THESTRSING;
				}
				System.out.printf(codeString);
				this.setClipboardContents(codeString);
				CopiedToClip = true;
				//System.exit(0);
				
			}
			
			
			
			
			
			for (int i = 0; i < this.Characters.getLength(); i++) {	
				if (this.Characters.get(i).getLetterPosition() == this.CURRENTTEST) {
					g.fillRect(this.Characters.get(i).getAvgPixelx() - 4,
							this.Characters.get(i).getAvgPixely() - 4, 8, 8);
					
					for(int s = 0; s < this.Characters.get(i).CharSpex.getLength(); s++){
						
						Spex curSpex = this.Characters.get(i).CharSpex.get(s);
						if(curSpex.getType() == Spex.PointType.LINE_START || curSpex.getType() == Spex.PointType.LINE_END){
							g.setColor(new Color(255,0,0));
						}
							
						else if(curSpex.getType() == Spex.PointType.ARC_START || curSpex.getType() == Spex.PointType.ARC_END){
							g.setColor(new Color(0,255,255));
						}
						g.drawOval((int)curSpex.getPosition().x, (int)curSpex.getPosition().y, 1, 1);
						g.drawOval((int)curSpex.getRotatedPosition().x, (int)curSpex.getRotatedPosition().y, 1, 1);
						
						g.setColor(new Color(0,0,0));
					}
					
					
					
					int ls=0,as=0;
					for(int s = 0; s < this.Characters.get(i).CharSpex.getLength(); s++){
						if(this.Characters.get(i).CharSpex.get(s).getType() == Spex.PointType.LINE_START){
							ls++;
						}
						else if(this.Characters.get(i).CharSpex.get(s).getType() == Spex.PointType.ARC_START){
							as++;
							
						}
						
					}
					
					int CHARACTER = ls*10 + as;
					int PIXELS = this.Characters.get(i).getPixelCount();
					int TL = this.Characters.get(i).getSpexTL();
					int TR = this.Characters.get(i).getSpexTR();
					int BL = this.Characters.get(i).getSpexBL();
					int BR = this.Characters.get(i).getSpexBR();
					
					String letter = new String();
					
					String predicted = this.Characters.get(i).getSpexCharacter(); //XXX 
					
					
					switch(CHARACTER){
					
						case 30: 
							letter = "4,1,A,F,7";
							if(PIXELS < 23){
								letter = "1";
								break;
							}
							
							if((TL + TR)-(BL+BR) == 2)
								letter = "A";
							
							if((BL+BR)-(TL + TR) == 2)
								letter = "4";
							
							if((BL+BR)-(TL + TR) == 0) {
								if(PIXELS > 25)
									letter = "F";
								
								if(PIXELS < 23)
									letter = "1";
							}
							
							break;
							
						case 31: 
							letter = "5,D";
							if(PIXELS < 39) //39 may be D though
								letter = "5";
							else if(PIXELS > 39)
								letter = "D";
							else
								if(TL%100 > 2)
									letter = "5";
							
							
							break;
							
						case 11: 
							letter = "6,9";
							if(BR == 200 || BL == 200)
								letter = "6";
							if(TR == 200 || TL == 200)
								letter = "9";
							break;
							
						case 21: 
							if(PIXELS < 36) 
								letter = "2"; 
							else
								letter = "9"; 
							break;
							
							
						case 2: 
							letter = "8";
							break;
							
						case 22: 
							letter = "0";
							break;
							
						case 53:
						case 52:
						case 42: 
							letter = "B";
							break;
							
						case 12: 
							letter = "3";
							break;
					
						case 40: //E
						case 50: //E
							if(PIXELS <= 24 && CHARACTER == 40)
								letter = "1";
							else
								if(TL*TR*BL*BR == 0)
									letter = "4";
								else
									letter = "E";
							break;
							
					}
					
					
					String buffer = new String();
					buffer = String.format("%s, Code: %d, Pixels: %d.::::: %d,%d,%d,%d", 
							//letter,
							predicted,
							CHARACTER,
							this.Characters.get(i).getPixelCount(),
							this.Characters.get(i).getSpexTL(),
							this.Characters.get(i).getSpexTR(),
							this.Characters.get(i).getSpexBL(),
							this.Characters.get(i).getSpexBR()); 					
					g.drawString(buffer, 100, 20);
					break;

				}
			}
			
		
			/*
			for (int i = 0; i < this.Spexs.getLength(); i++) {
				if(this.Spexs.get(i).getType() == Spex.PointType.LINE_START){
					g.setColor(new Color(255,0,0));
				}
				else if(this.Spexs.get(i).getType() == Spex.PointType.LINE_END){
					g.setColor(new Color(255,255,0));
				}
				else if(this.Spexs.get(i).getType() == Spex.PointType.ARC_START){
					g.setColor(new Color(255,0,255));
				}
				else if(this.Spexs.get(i).getType() == Spex.PointType.ARC_END){
					g.setColor(new Color(0,255,255));
				}
				g.drawOval((int)this.Spexs.get(i).getPosition().x, (int)this.Spexs.get(i).getPosition().y, 1, 1);
				g.setColor(new Color(0,0,0));
			}
			*/
			
			
			

			/*
			try {
				FileOutputStream os;
				os = new FileOutputStream("/home/thomas/test.pnm");

				
				byte[] allCharacters = new byte[CropSize*(CropSize*this.Characters.getLength())];
				
				for (int i = 0; i < this.Characters.getLength(); i++) {
					byte[] charBuffer = this.Characters.get(i).getPixelMapROTATEDByte();
					
			        for(int y=0 ;y < htsOcr.CropSize; y++){
			        	for(int x=0 ;x < htsOcr.CropSize; x++){
			        		allCharacters[x+i*htsOcr.CropSize +y*CropSize*this.Characters.getLength()] = charBuffer[x+y*htsOcr.CropSize];
			        	}
			        }
					
					
					
				}
				
				
				
				DataBufferByte dbuf =new DataBufferByte(allCharacters,CropSize*(CropSize*this.Characters.getLength()), 0);

				int bitMasks[] = new int[]{(byte)0xf}; 
				SampleModel sampleModel = new SinglePixelPackedSampleModel( 
							DataBuffer.TYPE_BYTE, (CropSize*this.Characters.getLength()), CropSize, bitMasks); 
				WritableRaster raster = Raster.createWritableRaster(sampleModel, dbuf, null); 
				//BufferedImage imageBuffer = new BufferedImage(colorModel, raster, false, null);
				
				
				BufferedImage imageBuffer = new BufferedImage(
						(CropSize*this.Characters.getLength()), CropSize, BufferedImage.TYPE_BYTE_BINARY );
				imageBuffer.setData(raster);
				
				//InputStream in = new ByteArrayInputStream(this.Characters.get(i).getPixelMapROTATEDByte());
				//BufferedImage imageBuffer = javax.imageio.ImageIO.read(in);
				
				
				javax.media.jai.JAI.create("encode", imageBuffer, os, "PNM", null); 
				os.close();
				
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
			*/
			
			
			for (int y = 0; y < htsOcr.CropSize; y++) {
				for (int x = 0; x < htsOcr.CropSize; x++) {
					if(this.CURRENTTEST >= this.Characters.getLength()){
						this.CURRENTTEST=0;
						this.repaint();
					}
					
					
					if (this.Characters.get(this.CURRENTTEST).getPixelMap()[x
							+ y * htsOcr.CropSize] != 0) {
						//g.fillRect(x, y, 1, 1);
					}
					if (this.Characters.get(this.CURRENTTEST).getPixelMapROTATED()[x
							+ y * htsOcr.CropSize] != 0) {
						g.fillRect(x + htsOcr.CropSize, y, 1, 1);
					}
				}
				
			}

			/*
			 * if(this.Characters != null && this.Characters.get(CURRENTTEST) !=
			 * null){
			 * g.fillRect(this.Characters.get(CURRENTTEST).getAvgPixelx()-5,
			 * this.Characters.get(CURRENTTEST).getAvgPixely()-5, 10, 10);
			 * System.out.printf("pos %d,deg %d,char %d\n",
			 * this.Characters.get(CURRENTTEST).DEBUG1,
			 * this.Characters.get(CURRENTTEST).DEBUG2,
			 * this.Characters.get(CURRENTTEST).getLetterPosition()); }
			 */

		}

	}
	
	public double getDistance(Vector2d point1,Vector2d point2) {
		Vector2d temp = new Vector2d(point1);
		temp.sub(point2);
		return temp.lengthSquared();
	}
	
	
	
	public class drawPrimative{
		public Vector2d pointA;
		public Vector2d pointB;
		
		public double sensitivety = 2.0;
		
		public drawPrimative(int x1,int y1, int x2, int y2){
			pointA = new Vector2d(x1,y1);
			pointB = new Vector2d(x2,y2);
		}
		
		
		boolean matchesPrimative(int x1,int y1, int x2, int y2){
			Vector2d inputPointA = new Vector2d(x1,y1);
			Vector2d inputPointB = new Vector2d(x2,y2);
			if( 
				((getDistance(pointA,inputPointA) < sensitivety && getDistance(pointB,inputPointB) < sensitivety))  || 
				getDistance(pointB,inputPointA) < sensitivety && getDistance(pointA,inputPointB) < sensitivety){
				return true;
			}
			
			return false;
		}
	}
	private org.apache.pivot.collections.ArrayList<drawPrimative> LinePrimatives = new org.apache.pivot.collections.ArrayList<drawPrimative>();
	private org.apache.pivot.collections.ArrayList<drawPrimative> ArcPrimatives = new org.apache.pivot.collections.ArrayList<drawPrimative>();
	
	
	
	
	
	
	public void drawIt(Graphics g) {
		int arrSize = drawData.length;
		int i;
		for (i = 0; i < arrSize;) {
			if (drawData[i + 2] >= 10){
				
				if(!this.SpexsAdded){
					
					
					boolean uniqueSpex = true;
					
					for(int z =0; z < LinePrimatives.getLength(); z++){
						if(LinePrimatives.get(z).matchesPrimative(drawData[i+0], drawData[i+1], drawData[i+2], drawData[i+3]) == true){
							uniqueSpex = false;
							break;
						}
					}
					
					if(uniqueSpex){
						LinePrimatives.add(new drawPrimative(drawData[i+0], drawData[i+1], drawData[i+2], drawData[i+3]));
						Spexs.add(new Spex(new Vector2d(drawData[i+0],drawData[i+1]), Spex.PointType.LINE_START));
						Spexs.add(new Spex(new Vector2d(drawData[i+2],drawData[i+3]), Spex.PointType.LINE_END));
					}
					
					
				}
				
				g.drawLine(drawData[i++], drawData[i++], drawData[i++],
						drawData[i++]);
			}

			else{
				this.drawArc(drawData[i++], drawData[i++], drawData[i++],
						drawData[i++], drawData[i++], g);
			}
		}
		if(!this.SpexsAdded){
			this.SpexsAdded = true;
		}
	}

	void drawArc(int x, int y, int r, int s, int e, Graphics g) {
		double torad = Math.PI / 180;
		int ss;
		double xx_last = -1, yy_last = -1;
		
		int first_xx=-1,last_xx=0;
		int first_yy=-1,last_yy=0;
		
		for (ss = s; ss <= s + e; ss += 8) {
			double xx = Math.round(x + r * Math.cos(ss * torad));
			double yy = Math.round(y - r * Math.sin(ss * torad));
			if (xx != xx_last || yy != yy_last) {
				
				if(first_xx == -1 || first_yy == -1){
					first_xx = (int) xx;
					first_yy = (int) yy;
				}
				else{
					last_xx = (int) xx;
					last_yy = (int) yy;
				}
				
				g.fillRect((int) xx, (int) yy, 1, 1);
				xx_last = xx;
				yy_last = yy;
			}
		}
		
		
		if(!this.SpexsAdded){

			boolean uniqueSpex = true;
			
			for(int z =0; z < ArcPrimatives.getLength(); z++){
				if(ArcPrimatives.get(z).matchesPrimative(first_xx, first_yy, last_xx, last_yy) == true){
					uniqueSpex = false;
					break;
				}
			}
			
			if(uniqueSpex){
				ArcPrimatives.add(new drawPrimative(first_xx, first_yy, last_xx, last_yy));
				Spexs.add(new Spex(new Vector2d(first_xx,first_yy), Spex.PointType.ARC_START));
				Spexs.add(new Spex(new Vector2d(last_xx,last_yy), Spex.PointType.ARC_END));
			}
		}
	}


	// A-origin, B & C the other points of the triangle, P the test point.
	public static boolean pointInsideTriangle(Vector2f A, Vector2f B, Vector2f C,
			Vector2f P) {

		Polygon poly = new Polygon();
		poly.addPoint((int) A.getX(), (int) A.getY());
		poly.addPoint((int) B.getX(), (int) B.getY());
		poly.addPoint((int) C.getX(), (int) C.getY());

		return poly.contains(new Point((int) P.getX(), (int) P.getY()));

	}
	@Override
	public void mouseClicked(MouseEvent e) {
		
	}
	
	
	public void initialPROCESSING(){

		BufferedImage bi = new BufferedImage(this.getWidth(), this.getHeight(),
				BufferedImage.TYPE_BYTE_GRAY);
		Graphics2D g2 = bi.createGraphics();
		
		this.CLICKED = true;
		this.POLLGraphics = true;
		paint(g2);
		g2.dispose();
		this.POLLGraphics = false;

		/*------------Calculate Origin-----------*/
		int[] pixelMap = new int[this.getWidth() * this.getHeight()];
		bi.getData().getPixels(0, 0, this.getWidth(), this.getHeight(),
				pixelMap);

		double xxsum = 0;
		double yysum = 0;
		double ccsum = 0;

		for (int y = 0; y < this.getHeight(); y++) {
			for (int x = 0; x < this.getWidth(); x++) {
				if (pixelMap[(x + y * this.getWidth())] != 0) {
					xxsum += x;
					yysum += y;
					ccsum += 1;
				}
			}
		}
		htsOcr.avg_x = (int) (xxsum / ccsum);
		htsOcr.avg_y = (int) (yysum / ccsum);
		/*------------Calculate Origin-----------*/

		for (int y = 0; y < this.getHeight(); y++) {
			for (int x = 0; x < this.getWidth(); x++) {
				if (pixelMap[(x + y * this.getWidth())] != 0) {

					int[] cropped = new int[htsOcr.CropSize * htsOcr.CropSize];
					recursiveCrop(x, y, x, y, cropped, pixelMap); // corrupts
																	// pixelmap
					Characters.add(new Character(x - CropAlignX,
							y - CropAlignY, cropped));
				}
			}
		}
		int length = 400;
		int origin_x = htsOcr.avg_x + SpiralAlignX;
		int origin_y = htsOcr.avg_y + SpiralAlignY;

		for (int deg = -5; deg < 360; deg += 10) {
			Vector2f A = new Vector2f(origin_x, origin_y);

			Vector2f B = new Vector2f((int) (origin_x + length
					* Math.cos(Math.toRadians(deg))), (int) (origin_y + length
					* Math.sin(Math.toRadians(deg))));

			Vector2f C = new Vector2f((int) (origin_x + length
					* Math.cos(Math.toRadians(deg + 10))),
					(int) (origin_y + length
							* Math.sin(Math.toRadians(deg + 10))));

			for (int i = 0; i < this.Characters.getLength(); i++) {
				if (this.Characters.get(i).getRotation() == -1) {
					Vector2f P = new Vector2f(this.Characters.get(i)
							.getAvgPixelx(), this.Characters.get(i)
							.getAvgPixely());
					if (pointInsideTriangle(A, B, C, P)) {
						this.Characters.get(i).setRotation(deg + 5 - 270);
					}
				}
			}
		}

		for (int i = 0; i < this.Characters.getLength(); i++) {
			this.Characters.get(i).setDistanceFromOrigin(new Vector2d(origin_x, origin_y));
			this.Characters.get(i).generateRotatedMap();
		}

		// ------------------------------------------------------------
		for (int deg = 0; deg < 360; deg += 10) {

			org.apache.pivot.collections.ArrayList<Character> CharBuffer = new org.apache.pivot.collections.ArrayList<Character>();

			for (int i = 0; i < this.Characters.getLength(); i++) {
				if (this.Characters.get(i).getRotation() == deg) {
					CharBuffer.add(this.Characters.get(i));
				}
			}

			Comparator<Character> distanceComparator = new Comparator<Character>() {

				@Override
				public int compare(Character o1, Character o2) {
					return o1.getDistanceFromOrigin()
							- o2.getDistanceFromOrigin();
				}

			};

			org.apache.pivot.collections.ArrayList.sort(CharBuffer,
					distanceComparator);

			for (int i = 0; i < CharBuffer.getLength(); i++) {
				int letterPosition = i * 36 + deg / 10;
				CharBuffer.get(i).setLetterPosition(letterPosition);
				CharBuffer.get(i).DEBUG1 = i;
				CharBuffer.get(i).DEBUG2 = deg;
			}
		}
		org.apache.pivot.collections.ArrayList.sort(this.Characters); 
		
		for (int i = 0; i < this.Characters.getLength(); i++) {
			Polygon charBorder = new Polygon();
			
			double borderSize = 16.5;//16.5
			
			if(i < 5){ //First ~5 are squished together
				borderSize = 14.8;
			}
			
			int pointTopLeftx = this.Characters.get(i).getAvgPixelx() + 
					(int) (borderSize*Math.cos(Math.toRadians(this.Characters.get(i).getRotation()-40-90)));
			int pointTopLefty = this.Characters.get(i).getAvgPixely() +
					(int) (borderSize*Math.sin(Math.toRadians(this.Characters.get(i).getRotation()-40-90)));
			
			int pointTopRightx = this.Characters.get(i).getAvgPixelx() + 
			(int) (borderSize*Math.cos(Math.toRadians(this.Characters.get(i).getRotation()-40)));
			
			int pointTopRighty = this.Characters.get(i).getAvgPixely() +
			(int) (borderSize*Math.sin(Math.toRadians(this.Characters.get(i).getRotation()-40)));
			
			
			int pointBottomLeftx = this.Characters.get(i).getAvgPixelx() + 
			(int) (borderSize*Math.cos(Math.toRadians(this.Characters.get(i).getRotation()-40-180)));
			
			int pointBottomLefty = this.Characters.get(i).getAvgPixely() +
			(int) (borderSize*Math.sin(Math.toRadians(this.Characters.get(i).getRotation()-40-180)));
			
			int pointBottomRightx = this.Characters.get(i).getAvgPixelx() + 
			(int) (borderSize*Math.cos(Math.toRadians(this.Characters.get(i).getRotation()-40-90-180)));
			
			int pointBottomRighty = this.Characters.get(i).getAvgPixely() +
			(int) (borderSize*Math.sin(Math.toRadians(this.Characters.get(i).getRotation()-40-90-180)));
			
			charBorder.addPoint(pointTopLeftx, pointTopLefty);
			charBorder.addPoint(pointTopRightx, pointTopRighty);
			charBorder.addPoint(pointBottomRightx, pointBottomRighty);
			charBorder.addPoint(pointBottomLeftx, pointBottomLefty);
			
			this.Characters.get(i).CharSpex.clear();
			
			for (int s = 0; s < this.Spexs.getLength(); s++) {	
				Spex currentSpex = this.Spexs.get(s);
				
				Point curSpexPos = new Point((int)currentSpex.getPosition().x,(int)currentSpex.getPosition().y);
				
				if(charBorder.contains(curSpexPos) && currentSpex.scannedThis() == false && currentSpex != null){
					this.Characters.get(i).CharSpex.add(currentSpex);
					currentSpex.setScanned();
				}
			}
			this.Characters.get(i).generateSpexMetrics();
			this.Characters.get(i).generateSpexRotation();
		}
		
		
	}

	public boolean checkPixelMap(int x, int y, int[] pixelMap) {
		if (x + y * this.getWidth() >= pixelMap.length
				|| pixelMap[x + y * this.getWidth()] == 0)
			return false;
		else
			return true;

	}

	public void recursiveCrop(int sx, int sy, int pivot_x, int pivot_y,
			int[] cropped, int[] pixelMap) {

		/*
		 * ooo oxo ooo
		 */

		if (checkPixelMap(pivot_x - 1, pivot_y - 1, pixelMap)) {

			recursiveCrop(sx, sy, pivot_x - 1, pivot_y - 1, cropped, pixelMap);

		}
		if (checkPixelMap(pivot_x, pivot_y - 1, pixelMap)) {// top

			recursiveCrop(sx, sy, pivot_x + 0, pivot_y - 1, cropped, pixelMap);

		}
		if (checkPixelMap(pivot_x + 1, pivot_y - 1, pixelMap)) {

			recursiveCrop(sx, sy, pivot_x + 1, pivot_y - 1, cropped, pixelMap);

		}

		if (checkPixelMap(pivot_x - 1, pivot_y + 0, pixelMap)) {

			recursiveCrop(sx, sy, pivot_x - 1, pivot_y + 0, cropped, pixelMap);

		}

		if (checkPixelMap(pivot_x, pivot_y, pixelMap)) { // center should always
															// be true

			pixelMap[(pivot_x + 0) + ((pivot_y + 0) * this.getWidth())] = 0;

			cropped[(pivot_x - sx + htsOcr.CropAlignX)
					+ ((pivot_y - sy + htsOcr.CropAlignY) * htsOcr.CropSize)] = 255;
		}

		if (checkPixelMap(pivot_x + 1, pivot_y, pixelMap)) {

			recursiveCrop(sx, sy, pivot_x + 1, pivot_y + 0, cropped, pixelMap);

		}

		if (checkPixelMap(pivot_x - 1, pivot_y + 1, pixelMap)) {

			recursiveCrop(sx, sy, pivot_x - 1, pivot_y + 1, cropped, pixelMap);

		}
		if (checkPixelMap(pivot_x, pivot_y + 1, pixelMap)) {// bottom

			recursiveCrop(sx, sy, pivot_x + 0, pivot_y + 1, cropped, pixelMap);

		}
		if (checkPixelMap(pivot_x + 1, pivot_y + 1, pixelMap)) {

			recursiveCrop(sx, sy, pivot_x + 1, pivot_y + 1, cropped, pixelMap);

		}
	}

	@Override
	public void mouseExited(MouseEvent e) {

	}

	@Override
	public void mousePressed(MouseEvent e) {

	}

	@Override
	public void mouseReleased(MouseEvent e) {

	}

	@Override
	public void mouseDragged(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseMoved(MouseEvent e) {
		this.mouse_x = e.getPoint().x;
		this.mouse_y = e.getPoint().y;
		e.consume();
		this.repaint();

	}

	@Override
	public void lostOwnership(Clipboard clipboard, Transferable contents) {
		// TODO Auto-generated method stub

	}

	public String getClipboardContents() {
		String result = "";
		Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
		// odd: the Object param of getContents is not currently used
		Transferable contents = clipboard.getContents(null);
		boolean hasTransferableText = (contents != null)
				&& contents.isDataFlavorSupported(DataFlavor.stringFlavor);
		if (hasTransferableText) {
			try {
				result = (String) contents
						.getTransferData(DataFlavor.stringFlavor);
			} catch (UnsupportedFlavorException ex) {
				// highly unlikely since we are using a standard DataFlavor
				// System.out.println(ex);
				// ex.printStackTrace();
				return null;
			} catch (IOException ex) {
				// System.out.println(ex);
				// ex.printStackTrace();
				return null;
			}
		}
		return result;
	}
	public void setClipboardContents(String data) {
		StringSelection stringSelection = new StringSelection( data );
	    Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
	    clipboard.setContents( stringSelection, this );

	}



	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

}
