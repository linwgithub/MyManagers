package com.linw.mymanager.common.utils;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.ActivityNotFoundException;
import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.content.res.Resources;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.util.Log;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;

public class PicUtil {

	private static final String LOG_TAG = PicUtil.class.getSimpleName();

	public static final int REQUEST_CODE_PICK_A_PHOTO = 0x100;
	public static final int REQUEST_CODE_CAPTURE_A_PHOTO = 0x101;
	public static final int REQUEST_CODE_CROP_PHOTO = 0x102;
	public static final int REQUEST_CODE_PICK_AND_CROP_PHOTO = 0x103;

	/**
	 * 从图库选择一张图并且进行裁剪。返回值有两种方式，如果returndata设为true则直接返回bitmap，请注意这种方法可能导致内存溢出；
	 * 如果returndata为false并且outputUri不等于null，则将裁剪后的图片保存在outputUri所指定的文件中
	 *
	 * @param context
	 *            用于调用startActivityForResult
	 * @param aspectX
	 *            X轴比例
	 * @param aspectY
	 *            Y轴比例
	 * @param outputX
	 *            输出文件的宽度
	 * @param outputY
	 *            输出文件的高度
	 * @param returndata
	 *            是否直接返回Bitmap,若是则返回的bitmap保存在返回intent的"data"字段中
	 * @param outputUri
	 *            若returndata=false才起作用，用于保存裁剪后的图片
	 * @param requestCode
	 *            用于作为startActivityForResult返回后的判断
	 */
	public static void cropPictureFromGallery(Activity context, int aspectX, int aspectY,
											  int outputX, int outputY, Boolean returndata, Uri outputUri, int requestCode) {

		// Launch picker to choose photo for selected contact
		Intent intent = new Intent(Intent.ACTION_GET_CONTENT, null);
		intent.setType("image/*");
		intent.putExtra("crop", "true");
		intent.putExtra("aspectX", aspectX);
		intent.putExtra("aspectY", aspectY);
		intent.putExtra("outputX", outputX);
		intent.putExtra("outputY", outputY);
		intent.putExtra("scale", true);
		intent.putExtra("outputFormat", Bitmap.CompressFormat.PNG.toString());

		// 目前这两个参数似乎不起作用
		// intent.putExtra("noFaceDetection", true);
		// intent.putExtra("circleCrop", false);

		intent.putExtra("return-data", returndata);

		if (!returndata && outputUri != null) {
			intent.putExtra(MediaStore.EXTRA_OUTPUT, outputUri);
		} else {
			intent.putExtra("return-data", true);
		}

		try {
			context.startActivityForResult(intent, requestCode);
		} catch (ActivityNotFoundException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 从图库选择一张图并且进行裁剪。返回值有两种方式，如果returndata设为true则直接返回bitmap，请注意这种方法可能导致内存溢出；
	 * 如果returndata为false并且outputUri不等于null，则将裁剪后的图片保存在outputUri所指定的文件中
	 *
	 * @param context
	 *            用于调用startActivityForResult
	 * @param aspectX
	 *            X轴比例
	 * @param aspectY
	 *            Y轴比例
	 * @param outputX
	 *            输出文件的宽度
	 * @param outputY
	 *            输出文件的高度
	 * @param returndata
	 *            是否直接返回Bitmap,若是则返回的bitmap保存在返回intent的"data"字段中
	 * @param outputUri
	 *            若returndata=false才起作用，用于保存裁剪后的图片
	 * @param requestCode
	 *            用于作为startActivityForResult返回后的判断
	 */
	public static void cropPictureFromGallery(Fragment context, int aspectX, int aspectY,
											  int outputX, int outputY, Boolean returndata, Uri outputUri, int requestCode) {

		// Launch picker to choose photo for selected contact
		Intent intent = new Intent(Intent.ACTION_GET_CONTENT, null);
		intent.setType("image/*");
		intent.putExtra("crop", "true");
		intent.putExtra("aspectX", aspectX);
		intent.putExtra("aspectY", aspectY);
		intent.putExtra("outputX", outputX);
		intent.putExtra("outputY", outputY);
		intent.putExtra("scale", true);
		intent.putExtra("outputFormat", Bitmap.CompressFormat.PNG.toString());

		// 目前这两个参数似乎不起作用
		// intent.putExtra("noFaceDetection", true);
		// intent.putExtra("circleCrop", false);

		intent.putExtra("return-data", returndata);

		if (!returndata && outputUri != null) {
			intent.putExtra(MediaStore.EXTRA_OUTPUT, outputUri);
		} else {
			intent.putExtra("return-data", true);
		}

		try {
			context.startActivityForResult(intent, requestCode);
		} catch (ActivityNotFoundException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 传入一张图片并且进行裁剪。返回值有两种方式，如果returndata设为true则直接返回bitmap，请注意这种方法可能导致内存溢出；
	 * 如果returndata为false并且outputUri不等于null，则将裁剪后的图片保存在outputUri所指定的文件中
	 *
	 * @param context
	 *            用于调用startActivityForResult
	 * @param picUri
	 *            需要裁剪的源图片的Uri，使用Uri.parse或者Uri.fromFile(file)来生成
	 * @param aspectX
	 *            X轴比例
	 * @param aspectY
	 *            Y轴比例
	 * @param outputX
	 *            输出文件的宽度
	 * @param outputY
	 *            输出文件的高度
	 * @param returndata
	 *            是否直接返回Bitmap,若是则返回的bitmap保存在返回intent的"data"字段中
	 * @param outputUri
	 *            若returndata=false才起作用，用于保存裁剪后的图片
	 * @param requestCode
	 *            用于作为startActivityForResult返回后的判断
	 */
	public static void cropPicture(Activity context, Uri picUri, int aspectX, int aspectY,
								   int outputX, int outputY, Boolean returndata, Uri outputUri, int requestCode) {

		Intent intent = new Intent("com.android.camera.action.CROP");

		// 不知道为什么这边必须使用setDataAndType，不可以先调用setData再调setType或者先调用setType再调用setData
		intent.setDataAndType(picUri, "image/*");

		intent.putExtra("crop", "true");
		intent.putExtra("aspectX", aspectX);
		intent.putExtra("aspectY", aspectY);
		intent.putExtra("outputX", outputX);
		intent.putExtra("outputY", outputY);
		intent.putExtra("scale", true);
		intent.putExtra("noFaceDetection", true);
		intent.putExtra("outputFormat", Bitmap.CompressFormat.PNG.toString());

		// 目前这两个参数似乎不起作用
		// intent.putExtra("noFaceDetection", true);
		// intent.putExtra("circleCrop", false);

		intent.putExtra("return-data", returndata);

		if (!returndata && outputUri != null) {
			intent.putExtra(MediaStore.EXTRA_OUTPUT, outputUri);
		} else {
			intent.putExtra("return-data", true);
		}

		try {
			context.startActivityForResult(intent, requestCode);
		} catch (ActivityNotFoundException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 传入一张图片并且进行裁剪。返回值有两种方式，如果returndata设为true则直接返回bitmap，请注意这种方法可能导致内存溢出；
	 * 如果returndata为false并且outputUri不等于null，则将裁剪后的图片保存在outputUri所指定的文件中
	 *
	 * @param context
	 *            用于调用startActivityForResult
	 * @param picUri
	 *            需要裁剪的源图片的Uri，使用Uri.parse或者Uri.fromFile(file)来生成
	 * @param aspectX
	 *            X轴比例
	 * @param aspectY
	 *            Y轴比例
	 * @param outputX
	 *            输出文件的宽度
	 * @param outputY
	 *            输出文件的高度
	 * @param returndata
	 *            是否直接返回Bitmap,若是则返回的bitmap保存在返回intent的"data"字段中
	 * @param outputUri
	 *            若returndata=false才起作用，用于保存裁剪后的图片
	 * @param requestCode
	 *            用于作为startActivityForResult返回后的判断
	 */
	public static void cropPicture(Fragment context, Uri picUri, int aspectX, int aspectY,
								   int outputX, int outputY, Boolean returndata, Uri outputUri, int requestCode) {

		Intent intent = new Intent("com.android.camera.action.CROP");

		// 不知道为什么这边必须使用setDataAndType，不可以先调用setData再调setType或者先调用setType再调用setData
		intent.setDataAndType(picUri, "image/*");

		intent.putExtra("crop", "true");
		intent.putExtra("aspectX", aspectX);
		intent.putExtra("aspectY", aspectY);
		intent.putExtra("outputX", outputX);
		intent.putExtra("outputY", outputY);
		intent.putExtra("scale", true);
		intent.putExtra("noFaceDetection", true);
		intent.putExtra("outputFormat", Bitmap.CompressFormat.PNG.toString());

		// 目前这两个参数似乎不起作用
		// intent.putExtra("noFaceDetection", true);
		// intent.putExtra("circleCrop", false);

		intent.putExtra("return-data", returndata);

		if (!returndata && outputUri != null) {
			intent.putExtra(MediaStore.EXTRA_OUTPUT, outputUri);
		} else {
			intent.putExtra("return-data", true);
		}

		try {
			context.startActivityForResult(intent, requestCode);
		} catch (ActivityNotFoundException e) {
			e.printStackTrace();
		}
	}

	public static Bitmap scaleBitmap(Bitmap src, int dstX, int dstY) {

		if (src == null) {
			return null;
		}

		Bitmap dst = null;
		/*
		 * Matrix matrix = new Matrix(); float srcX = (float)src.getWidth();
		 * float srcY = (float)src.getHeight();
		 * 
		 * float fWidthScale = ((float)dstX) / (srcX); float fHeightScale =
		 * ((float)dstY) / (srcY);
		 * 
		 * matrix.postScale(fWidthScale, fHeightScale); dst =
		 * Bitmap.createBitmap(src, 0, 0, dstX, dstY, matrix, true);
		 */

		if (dstX != src.getWidth() && dstY != src.getHeight()) {
			dst = Bitmap.createScaledBitmap(src, dstX, dstY, false);
			src.recycle();
		} else {
			dst = src;
		}

		return dst;
	}

	/**
	 * 将图片裁剪为圆角矩形,该函数无法指定圆角矩形的大小，默认与源图片保持一致
	 *
	 * @param src
	 *            源图片
	 * @param outerRadiusRat
	 *            圆角的大小
	 * @param srcRecycle
	 *            是否在完成后回收源图片
	 * @return 圆角图片
	 */
	public static Bitmap createRoundRectPicNoScaled(Bitmap src, float outerRadiusRat, Boolean srcRecycle) {

		if (src == null) {
			return null;
		}

		int width = src.getWidth();
		int height = src.getHeight();

		// 新建一个新的输出图片
		Bitmap dst = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
		Canvas canvas = new Canvas(dst);

		// 新建一个矩形
		RectF outerRect = new RectF(0, 0, width, height);

		// 产生一个圆角矩形，颜色无所谓，只要Alpha为1.0
		Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
		paint.setColor(Color.RED);
		canvas.drawRoundRect(outerRect, outerRadiusRat, outerRadiusRat, paint);

		// 将源图片绘制到这个圆角矩形上
		paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
		canvas.drawBitmap(src, 0, 0, paint);

		if (srcRecycle) {
			src.recycle();
		}

		return dst;
	}

	/**
	 * 将图片转化为指定大小的圆角矩形，可能对源图片进行缩放，使其短边一致让其占满目标矩形
	 *
	 * @param src
	 *            源图片
	 * @param rect
	 *            指定的大小
	 * @param outerRadiusRat
	 *            圆角的大小
	 * @param srcRecycle
	 *            是否在完成后回收源图片
	 * @return 圆角图片
	 */
	public static Bitmap createRoundRectPicScaled(Bitmap src, Rect rect, float outerRadiusRat,
												  Boolean srcRecycle) {

		if (src == null) {
			return null;
		}


		int src_width = src.getWidth();
		int src_height = src.getHeight();
		int dst_width = rect.width();
		int dst_height = rect.height();

		float rateWidth = ((float) dst_width) / ((float) src_width);
		float rateHeight = ((float) dst_height) / ((float) src_height);

		Rect srcRect = new Rect();
		if (rateWidth >= rateHeight) {
			int offset = (int) ((src_height - dst_height / rateWidth) / 2);
			srcRect.set(0, offset, src_width, src_height - offset);
		} else {
			int offset = (int) ((src_width - dst_width / rateHeight) / 2);
			srcRect.set(offset, 0, src_width - offset, src_height);
		}

		// 新建一个新的输出图片
		Bitmap dst = Bitmap.createBitmap(dst_width, dst_height, Bitmap.Config.ARGB_8888);
		Canvas canvas = new Canvas(dst);

		// 新建一个矩形
		RectF outerRect = new RectF(0, 0, dst_width, dst_height);

		// 产生一个圆角矩形，颜色无所谓，只要Alpha为1.0
		Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
		paint.setColor(Color.RED);
		canvas.drawRoundRect(outerRect, outerRadiusRat, outerRadiusRat, paint);

		// 将源图片绘制到这个圆角矩形上
		paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
		canvas.drawBitmap(src, srcRect, outerRect, paint);

		if (srcRecycle) {
			src.recycle();
		}

		return dst;
	}

	/**
	 * 将图片转化为指定大小的圆角矩形，可能对源图片进行缩放，使其短边一致让其占满目标矩形
	 *
	 * @param uri
	 *            源图片uri，一般是ContentProvider返回的地址
	 * @param resolver
	 *            ContentResolver
	 * @param rect
	 *            指定的大小
	 * @param outerRadiusRat
	 *            圆角的大小
	 * @return 圆角图片
	 */
	public static Bitmap createRoundRectPicScaled(Uri uri, ContentResolver resolver, Rect rect,
												  float outerRadiusRat) {
		String fi = "";
		String[] proj = { MediaStore.Images.Media.DATA };
		Cursor cursor = resolver.query(uri, proj, null, null, null);
		if (cursor != null) {
			int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
			if (cursor.getCount() > 0 && cursor.moveToFirst()) {
				fi = cursor.getString(column_index);
			}
			cursor.close();
			cursor = null;
		}
		Bitmap src = loadPictureInSampleSize(fi, null, rect.height(), rect.width());
		return createRoundRectPicScaled(src, rect, outerRadiusRat, true);
	}

	/**
	 * 将图片转化为指定大小的圆角矩形，可能对源图片进行缩放，使其短边一致让其占满目标矩形
	 *
	 * @param fi
	 *            源图片地址
	 * @param rect
	 *            指定的大小
	 * @param outerRadiusRat
	 *            圆角的大小
	 * @return 圆角图片
	 */
	public static Bitmap createRoundRectPicScaled(String fi, Rect rect, float outerRadiusRat) {
		Bitmap src = loadPictureInSampleSize(fi, null, rect.height(), rect.width());
		return createRoundRectPicScaled(src, rect, outerRadiusRat, true);
	}

	/**
	 * 将图片转化为指定大小的圆角矩形，可能对源图片进行缩放，使其短边一致让其占满目标矩形
	 *
	 * @param res
	 *            Resources对象
	 * @param resId
	 *            输入图片资源ID
	 * @param rect
	 *            指定的大小
	 * @param outerRadiusRat
	 *            圆角的大小
	 * @return 圆角图片
	 */
	public static Bitmap createRoundRectPicScaled(Resources res, int resId, Rect rect, float outerRadiusRat) {
		Bitmap src = loadPictureInSampleSize(res, resId, null, rect.height(), rect.width());
		return createRoundRectPicScaled(src, rect, outerRadiusRat, true);
	}

	/**
	 * 将Bitmap转化为带有描边的圆角矩形Bitmap
	 *
	 * @param src
	 *            源图片
	 * @param outerRadiusRat
	 *            圆角矩形的圆角的半径
	 * @param strokWidth
	 *            描边的宽度
	 * @param strokeColor
	 *            描边颜色
	 * @param srcRecycle
	 *            是否在完成后将源图片回收
	 * @return 转化后的图片
	 */
	public static Bitmap createRoundRectStrokePicNoScaled(Bitmap src, float outerRadiusRat, int strokWidth,
														  int strokeColor, Boolean srcRecycle) {

		if (src == null) {
			return null;
		}

		Bitmap tempSrc = createRoundRectPicNoScaled(src, outerRadiusRat, srcRecycle);

		int width = tempSrc.getWidth() + strokWidth * 2;
		int height = tempSrc.getHeight() + strokWidth * 2;

		outerRadiusRat += strokWidth;

		Bitmap dst = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
		Canvas canvas = new Canvas(dst);

		Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
		paint.setColor(strokeColor);
		canvas.drawRoundRect(new RectF(0, 0, width, height), outerRadiusRat, outerRadiusRat, paint);

		Rect srcRect = new Rect(0, 0, tempSrc.getWidth(), tempSrc.getHeight());
		Rect dstRect = new Rect(strokWidth, strokWidth, width - strokWidth, height - strokWidth);

		canvas.drawBitmap(tempSrc, srcRect, dstRect, null);

		tempSrc.recycle();

		return dst;
	}

	/**
	 * 将图片转化为指定大小的矩形，可能对源图片进行缩放，使其短边一致让其占满目标矩形
	 *
	 * @param fi
	 *            源图片地址
	 * @param rect
	 *            指定的大小
	 * @return 图片
	 */
	public static Bitmap createRectPicScaled(String fi, Rect rect) {
		Bitmap src = loadPictureInSampleSize(fi, null, rect.height(), rect.width());
		return createRectPicScaled(src, rect , true);
	}
	/**
	 * 将图片转化为指定大小的矩形，可能对源图片进行缩放，使其短边一致让其占满目标矩形
	 *
	 * @param res
	 *            Resources对象
	 * @param resId
	 *            输入图片资源ID
	 * @param rect
	 *            指定的大小
	 * @return 图片
	 */
	public static Bitmap createRectPicScaled(Resources res, int resId, Rect rect) {
		Bitmap src = loadPictureInSampleSize(res, resId, null, rect.height(), rect.width());
		return createRectPicScaled(src, rect , true);
	}
	/**
	 * 将图片转化为指定大小的矩形，可能对源图片进行缩放，使其短边一致让其占满目标矩形
	 *
	 * @param src
	 *            源图片
	 * @param rect
	 *            指定的大小
	 * @param srcRecycle
	 *            是否在完成后回收源图片
	 * @return 图片
	 */
	public static Bitmap createRectPicScaled(Bitmap src, Rect rect, Boolean srcRecycle) {

		if (src == null) {
			return null;
		}

		int src_width = src.getWidth();
		int src_height = src.getHeight();
		int dst_width = rect.width();
		int dst_height = rect.height();

		float rateWidth = ((float) dst_width) / ((float) src_width);
		float rateHeight = ((float) dst_height) / ((float) src_height);

		Rect srcRect = new Rect();
		if (rateWidth >= rateHeight) {
			int offset = (int) ((src_height - dst_height / rateWidth) / 2);
			srcRect.set(0, offset, src_width, src_height - offset);
		} else {
			int offset = (int) ((src_width - dst_width / rateHeight) / 2);
			srcRect.set(offset, 0, src_width - offset, src_height);
		}

		// 新建一个新的输出图片
		Bitmap dst = Bitmap.createBitmap(dst_width, dst_height, Bitmap.Config.ARGB_8888);
		Canvas canvas = new Canvas(dst);

		// 新建一个矩形
		RectF outerRect = new RectF(0, 0, dst_width, dst_height);

		canvas.drawBitmap(src, srcRect, outerRect, null);

		if (srcRecycle) {
			src.recycle();
		}

		return dst;
	}

	/**
	 * 将Bitmap转化为带有描边的矩形Bitmap
	 *
	 * @param src
	 *            源图片
	 * @param strokWidth
	 *            描边的宽度
	 * @param strokeColor
	 *            描边颜色
	 * @param inBound
	 *            描边是在源图片范围内或者是在范围外
	 * @param srcRecycle
	 *            是否在完成后将源图片回收
	 * @return 转化后的图片
	 */
	public static Bitmap createRectStrokePicNoScaled(Bitmap src, int strokWidth, int strokeColor,
													 Boolean inBound, Boolean srcRecycle) {

		if (src == null) {
			return null;
		}

		int width = src.getWidth();
		int height = src.getHeight();

		if (!inBound) {
			width += strokWidth * 2;
			height += strokWidth * 2;
		}

		Bitmap dst = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
		Canvas canvas = new Canvas(dst);

		Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
		paint.setColor(strokeColor);
		canvas.drawRect(new Rect(0, 0, width, height), paint);

		Rect srcRect = new Rect();
		Rect dstRect = new Rect(strokWidth, strokWidth, width - strokWidth, height - strokWidth);
		if (inBound) {
			srcRect.set(strokWidth, strokWidth, width - strokWidth, height - strokWidth);
		} else {
			srcRect.set(0, 0, src.getWidth(), src.getHeight());
		}
		canvas.drawBitmap(src, srcRect, dstRect, null);

		if (srcRecycle) {
			src.recycle();
		}

		return dst;
	}

	/**
	 * 将图片居中裁剪为圆形Bitmap，可能对源图片进行缩放，使其短边一致让其占满目标圆的正方形范围
	 *
	 * @param src
	 *            源图片
	 * @param outerRadiusRat
	 *            圆形的半径
	 * @param srcRecycle
	 *            是否在完成后回收源图片
	 * @return 转化后的图片
	 */
	public static Bitmap createCirclePicScaled(Bitmap src, float outerRadiusRat, Boolean srcRecycle) {

		if (src == null) {
			return null;
		}

		int width = (int)(2*outerRadiusRat);
		Bitmap tempSrc = createRectPicScaled(src, new Rect(0, 0, width, width), srcRecycle);
		Bitmap dstBitmap = createCirclePicNoScaled(tempSrc, outerRadiusRat, true);
		return dstBitmap;
	}
	/**
	 * 将图片居中裁剪为圆形Bitmap，输出图片的尺寸保持和源图片一致
	 *
	 * @param src
	 *            源图片
	 * @param outerRadiusRat
	 *            圆形的半径
	 * @param srcRecycle
	 *            是否在完成后回收源图片
	 * @return 转化后的图片
	 */
	public static Bitmap createCirclePicNoScaled(Bitmap src, float outerRadiusRat, Boolean srcRecycle) {

		if (src == null) {
			return null;
		}

		int width = src.getWidth();
		int height = src.getHeight();

		// 新建一个新的输出图片
		Bitmap output = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
		Canvas canvas = new Canvas(output);

		// 产生一个圆形，颜色无所谓，只要Alpha为1.0
		Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
		paint.setColor(Color.RED);
		canvas.drawCircle(width / 2, height / 2, outerRadiusRat, paint);

		// 将源图片绘制到这个圆形上
		paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
		canvas.drawBitmap(src, 0, 0, paint);

		if (srcRecycle) {
			src.recycle();
		}

		return output;
	}

	/**
	 * 将Bitmap转化为圆形带有描边的Bitmap，可能对源图片进行缩放，使其短边一致让其占满目标圆的正方形范围
	 *
	 * @param fi
	 *            源图片地址
	 * @param outerRadiusRat
	 *            圆形的半径
	 * @param strokWidth
	 *            描边的宽度
	 * @param strokeColor
	 *            描边颜色
	 * @return 转化后的图片
	 */
	public static Bitmap createCircleStrokePicScaled(String fi, float outerRadiusRat, int strokWidth,
													 int strokeColor) {
		int width = (int)(outerRadiusRat)*2;
		Bitmap src = loadPictureInSampleSize(fi, null, width, width);
		return createCircleStrokePicScaled(src, outerRadiusRat, strokWidth, strokeColor, true);
	}

	/**
	 * 将Bitmap转化为圆形带有描边的Bitmap，可能对源图片进行缩放，使其短边一致让其占满目标圆的正方形范围
	 *
	 * @param src
	 *            源图片
	 * @param outerRadiusRat
	 *            圆形的半径
	 * @param strokWidth
	 *            描边的宽度
	 * @param strokeColor
	 *            描边颜色
	 * @param srcRecycle
	 *            是否在完成后将源图片回收
	 * @return 转化后的图片
	 */
	public static Bitmap createCircleStrokePicScaled(Bitmap src, float outerRadiusRat, int strokWidth,
													 int strokeColor, Boolean srcRecycle) {

		if (src == null) {
			return null;
		}

		Bitmap tempSrc = createCirclePicScaled(src, outerRadiusRat, srcRecycle);

		int width = tempSrc.getWidth() + strokWidth * 2;
		int height = tempSrc.getHeight() + strokWidth * 2;

		outerRadiusRat += strokWidth;

		Bitmap dst = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
		Canvas canvas = new Canvas(dst);

		Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
		paint.setColor(strokeColor);
		canvas.drawCircle(width / 2, height / 2, outerRadiusRat, paint);

		Rect srcRect = new Rect(0, 0, tempSrc.getWidth(), tempSrc.getHeight());
		Rect dstRect = new Rect(strokWidth, strokWidth, width - strokWidth, height - strokWidth);

		canvas.drawBitmap(tempSrc, srcRect, dstRect, null);

		tempSrc.recycle();

		return dst;
	}

	/**
	 * 使用options.inSampleSize来将输入图片转换为目标尺寸，缺点是这种方法只能实现成倍的放大缩小，但过程中不需要载入完整的图片，
	 * 一般不会导致内存溢出
	 *
	 * @param res
	 *            Resources对象
	 * @param resId
	 *            输入图片资源ID
	 * @param fo
	 *            输出图片的文件路径，若为null，则直接返回bitmap
	 * @param destH
	 *            目标高度
	 * @param destW
	 *            目标宽度
	 * @return 若fo为null，则返回转换后的bitmap，若fo不为null且保存到目标文件成功，则返回null
	 */
	public static Bitmap loadPictureInSampleSize(Resources res, int resId, String fo, int destH, int destW) {
		Bitmap mBitmap = null;

		BitmapFactory.Options options = new BitmapFactory.Options();

		// 该属性的作用是仅仅载入图片的信息而不载入图片本身
		options.inJustDecodeBounds = true;
		// 由于设置inJustDecodeBounds为true，因此执行下面代码后bitmap为空
		mBitmap = BitmapFactory.decodeResource(res, resId, options);

		options.inSampleSize = calculateInSampleSize(options, destW, destH);
		options.inJustDecodeBounds = false;
		// 重新读入图片，注意此时已经把options.inJustDecodeBounds设回false
		mBitmap = BitmapFactory.decodeResource(res, resId, options);

		// 这样我们就可以读取较大的图片而不会出现内存溢出问题了。
		// 如果你想把压缩后的图片保存在sdcard上的话，通过如下代码就可以了：
		if (saveBitmapToFile(mBitmap, fo)) {
			mBitmap.recycle();
			mBitmap = null;
		}

		return mBitmap;
	}
	/**
	 * 使用options.inSampleSize来将输入图片转换为目标尺寸，缺点是这种方法只能实现成倍的放大缩小，但过程中不需要载入完整的图片，
	 * 一般不会导致内存溢出
	 *
	 * @param fi
	 *            输入图片的文件路径
	 * @param fo
	 *            输出图片的文件路径，若为null，则直接返回bitmap
	 * @param destH
	 *            目标高度
	 * @param destW
	 *            目标宽度
	 * @return 若fo为null，则返回转换后的bitmap，若fo不为null且保存到目标文件成功，则返回null
	 */
	public static Bitmap loadPictureInSampleSize(String fi, String fo, int destH, int destW) {
		Bitmap mBitmap = null;
		File file = new File(fi);
		if (!file.exists()||!file.isFile()) {
			return null;
		}
		BitmapFactory.Options options = new BitmapFactory.Options();

		// 该属性的作用是仅仅载入图片的信息而不载入图片本身
		options.inJustDecodeBounds = true;
		// 由于设置inJustDecodeBounds为true，因此执行下面代码后bitmap为空
		mBitmap = BitmapFactory.decodeFile(fi, options);

		// 计算缩放比例，由于是固定比例缩放，分别计算宽高的缩放比例，取比例小者作为最终缩放比例,防止由于长宽比例不同导致图片被过度缩小
		// 因为结果为int型，如果相除后值为0.n，则最终结果将是0;
		int scaleH = (int) (options.outHeight / (float) destH);
		scaleH = scaleH >= 0 ? scaleH : 1;
		int scaleW = (int) (options.outWidth / (float) destW);
		scaleW = scaleW >= 0 ? scaleW : 1;
		int scale = scaleH <= scaleW ? scaleH : scaleW;

		options.inSampleSize = scale;
		options.inJustDecodeBounds = false;
		// 重新读入图片，注意此时已经把options.inJustDecodeBounds设回false
		mBitmap = BitmapFactory.decodeFile(fi, options);

		// 这样我们就可以读取较大的图片而不会出现内存溢出问题了。
		// 如果你想把压缩后的图片保存在sdcard上的话，通过如下代码就可以了：
		if (saveBitmapToFile(mBitmap, fo)) {
			mBitmap.recycle();
			mBitmap = null;
		}

		return mBitmap;
	}

	/**
	 * 使用matrix来将输入图片精确转换为目标尺寸，缺点是过程中需要载入完整的图片，可能导致内存溢出。该方法为stretch，可能导致图片拉伸
	 *
	 * @param fi
	 *            输入图片的文件路径
	 * @param fo
	 *            输出图片的文件路径，若为null，则直接返回bitmap
	 * @param destH
	 *            目标高度
	 * @param destW
	 *            目标宽度
	 * @return 若fo为null，则返回转换后的bitmap，若fo不为null且保存到目标文件成功，则返回null
	 */
	public static Bitmap loadPictureInFixedSizeStretch(String fi, String fo, int destH, int destW) {
		Bitmap mBitmap = null;
		File file = new File(fi);
		if (!file.exists()||!file.isFile()) {
			return null;
		}
		Bitmap resizeBitmap = null;

		BitmapFactory.Options options = new BitmapFactory.Options();
		options.inJustDecodeBounds = false;

		// 由于设置inJustDecodeBounds为true，因此执行下面代码后bitmap为空
		mBitmap = BitmapFactory.decodeFile(fi, options);

		int bmpWidth = mBitmap.getWidth();
		int bmpHeight = mBitmap.getHeight();

		// 缩放图片的尺寸
		float scaleWidth = (float) destW / bmpWidth; // 按固定大小缩放 destW 写多大就多大
		float scaleHeight = (float) destH / bmpHeight; // 按固定大小缩放 destH 写多大就多大

		Matrix matrix = new Matrix();
		matrix.postScale(scaleWidth, scaleHeight);// 产生缩放后的Bitmap对象

		resizeBitmap = Bitmap.createBitmap(mBitmap, 0, 0, bmpWidth, bmpHeight, matrix, false);
		mBitmap.recycle();
		mBitmap = null;

		// 如果你想把压缩后的图片保存在sdcard上的话，通过如下代码就可以了：
		if (saveBitmapToFile(resizeBitmap, fo)) {
			resizeBitmap.recycle();
			resizeBitmap = null;
		}

		return resizeBitmap;
	}

	/**
	 * 使用matrix来将输入图片精确转换为目标尺寸，缺点是过程中需要载入完整的图片，可能导致内存溢出。该方法不会导致图片拉伸，而是会缩放并裁剪图片
	 *
	 * @param fi
	 *            输入图片的文件路径
	 * @param fo
	 *            输出图片的文件路径，若为null，则直接返回bitmap
	 * @param destH
	 *            目标高度
	 * @param destW
	 *            目标宽度
	 * @return 若fo为null，则返回转换后的bitmap，若fo不为null且保存到目标文件成功，则返回null
	 */
	public static Bitmap loadPictureInFixedSizeNoStretch(String fi, String fo, int destH, int destW) {

		File file = new File(fi);
		if (!file.exists()||!file.isFile()) {
			return null;
		}

		Rect dstRect = new Rect(0, 0, destW, destH);
		Bitmap resizeBitmap = createRectPicScaled(fi, dstRect);

		// 如果你想把压缩后的图片保存在sdcard上的话，通过如下代码就可以了：
		if (saveBitmapToFile(resizeBitmap, fo)) {
			resizeBitmap.recycle();
			resizeBitmap = null;
		}

		return resizeBitmap;
	}

	/**
	 * Calculate an inSampleSize for use in a {@link BitmapFactory.Options} object when decoding
	 * bitmaps using the decode* methods from {@link BitmapFactory}. This implementation calculates
	 * the closest inSampleSize that will result in the final decoded bitmap having a width and
	 * height equal to or larger than the requested width and height. This implementation does not
	 * ensure a power of 2 is returned for inSampleSize which can be faster when decoding but
	 * results in a larger bitmap which isn't as useful for caching purposes.
	 *
	 * @param options An options object with out* params already populated (run through a decode*
	 *            method with inJustDecodeBounds==true
	 * @param reqWidth The requested width of the resulting bitmap
	 * @param reqHeight The requested height of the resulting bitmap
	 * @return The value to be used for inSampleSize
	 */
	public static int calculateInSampleSize(BitmapFactory.Options options,
											int reqWidth, int reqHeight) {
		// Raw height and width of image
		final int height = options.outHeight;
		final int width = options.outWidth;
		int inSampleSize = 1;

		if (height > reqHeight || width > reqWidth) {

			// Calculate ratios of height and width to requested height and width
			final int heightRatio = Math.round((float) height / (float) reqHeight);
			final int widthRatio = Math.round((float) width / (float) reqWidth);

			// Choose the smallest ratio as inSampleSize value, this will guarantee a final image
			// with both dimensions larger than or equal to the requested height and width.
			inSampleSize = heightRatio < widthRatio ? heightRatio : widthRatio;

			// This offers some additional logic in case the image has a strange
			// aspect ratio. For example, a panorama may have a much larger
			// width than height. In these cases the total pixels might still
			// end up being too large to fit comfortably in memory, so we should
			// be more aggressive with sample down the image (=larger inSampleSize).

			final float totalPixels = width * height;

			// Anything more than 2x the requested pixels we'll sample down further
			final float totalReqPixelsCap = reqWidth * reqHeight * 2;

			while (totalPixels / (inSampleSize * inSampleSize) > totalReqPixelsCap) {
				inSampleSize++;
			}
		}
		return inSampleSize;
	}

	public static int[] getPictureSize(String pictureUri) {
		int[] ret = new int[2];
		ret[0] = 0;
		ret[1] = 0 ;

		File file = new File(pictureUri);
		if (file.exists() && file.isFile()) {
			BitmapFactory.Options options = new BitmapFactory.Options();
			// 该属性的作用是仅仅载入图片的信息而不载入图片本身
			options.inJustDecodeBounds = true;
			// 由于设置inJustDecodeBounds为true，因此执行下面代码后bitmap为空
			BitmapFactory.decodeFile(pictureUri, options);

			ret[0] = options.outWidth;
			ret[1] = options.outHeight;
		}

		return ret;
	}

	public static int getExifRotation(String pictureUri) {
		int rotate = 0;

		try {
			ExifInterface exif = new ExifInterface(pictureUri);
			int tag = exif.getAttributeInt(ExifInterface.TAG_ORIENTATION, -1);
			switch (tag) {
				case ExifInterface.ORIENTATION_NORMAL:
					rotate = 0;
					break;
				case ExifInterface.ORIENTATION_ROTATE_90:
					rotate = 90;
					break;
				case ExifInterface.ORIENTATION_ROTATE_180:
					rotate = 180;
					break;
				case ExifInterface.ORIENTATION_ROTATE_270:
					rotate = 270;
					break;
				default:
					break;
			}
		} catch (Exception e) {
			e.printStackTrace();
			Log.e(LOG_TAG, "getExifRotation failed: " + e.getMessage() + " for " + pictureUri);
		}

		return rotate;
	}

	public static Bitmap getPhotoRawBitmap(String photoPath) {
		if (photoPath == null || photoPath.length() == 0) {
			return null;
		}

		Bitmap bmp = null;
		File file = new File(photoPath);
		if (file.exists() && file.isFile()) {
			bmp = BitmapFactory.decodeFile(photoPath, null);
		}
		return bmp;
	}

	public static Bitmap rotateBitmap(Bitmap srcBmp, int rotateAngle, boolean srcRecycle) {
		if (srcBmp == null) {
			return null;
		}

		Matrix matrix = new Matrix();
		matrix.postRotate(rotateAngle);
		Bitmap bmp = Bitmap.createBitmap(srcBmp, 0, 0, srcBmp.getWidth(), srcBmp.getHeight(), matrix, true);

		if (srcRecycle == true) {
			srcBmp.recycle();
			srcBmp = null;
		}

		return bmp;
	}

	/**
	 * 将Bitmap保存到指定路径，默认保存为PNG格式
	 *
	 * @param bitmap
	 *            待保存的Bitmap
	 * @param fo
	 *            文件输出位置
	 * @return 保存成功则返回true，失败返回false
	 */
	public static Boolean saveBitmapToFile(Bitmap bitmap, String fo) {
		return saveBitmapToFile(bitmap, fo, Bitmap.CompressFormat.PNG, 100);
	}

	public static Boolean saveBitmapToFile(Bitmap bitmap, String fo, Bitmap.CompressFormat compressFormat, int quality) {
		Boolean ret = false;
		if (bitmap!=null && fo != null) {
			File file = new File(fo);
			try {
				if (!file.exists()||!file.isFile()) {
					file.createNewFile();
				}

				// 记得添加sdcard读写权限
				FileOutputStream out = new FileOutputStream(file);
				if (bitmap.compress(compressFormat, quality, out)) {
					out.flush();
					out.close();
				}

				ret = true;
			} catch (Exception e) {
				e.printStackTrace();
				Log.e(LOG_TAG, e.getMessage());
			}
		}
		return ret;
	}

	/**
	 * 将由contentProvider返回的Uri保存到本地文件中
	 *
	 * @param uri
	 *            contentProvider返回的文件Uri，一般是Gallery返回的图片位置
	 * @param context
	 * @param fo
	 *            目标文件
	 * @return 是否保存成功
	 */

	public static Boolean saveContentPicToFile(Uri uri, Context context, String fo) {
		final boolean isKitKat = Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT;

		Boolean ret = false;
		if (uri != null && context != null && fo != null) {
			String fi = "";

			if (isKitKat) {
				fi = getContentPicPath(context, uri);
			} else {
				fi = getDataColumn(context, uri, null, null);
			}

			if (fi == null || fi.length() == 0) {
				return false;
			}

			File toFile = new File(fo);
			File fromFile = new File(fi);
			if (!fromFile.isFile() || !toFile.isFile()) {
				return false;
			}

			ret = copyfile(fromFile, toFile);
		}
		return ret;
	}

	@TargetApi(19)
	private static String getContentPicPath(final Context context, final Uri uri) {
		final boolean isKitKat = Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT;

		if (isKitKat && DocumentsContract.isDocumentUri(context, uri)) {
			// ExternalStorageProvider  
			if (isExternalStorageDocument(uri)) {
				final String docId = DocumentsContract.getDocumentId(uri);
				final String[] split = docId.split(":");
				final String type = split[0];

				if ("primary".equalsIgnoreCase(type)) {
					return Environment.getExternalStorageDirectory() + "/" + split[1];
				}

				// TODO handle non-primary volumes
			}
			// DownloadsProvider
			else if (isDownloadsDocument(uri)) {

				final String id = DocumentsContract.getDocumentId(uri);
				final Uri contentUri = ContentUris.withAppendedId(
						Uri.parse("content://downloads/public_downloads"), Long.valueOf(id));

				return getDataColumn(context, contentUri, null, null);
			}
			// MediaProvider
			else if (isMediaDocument(uri)) {
				final String docId = DocumentsContract.getDocumentId(uri);
				final String[] split = docId.split(":");
				final String type = split[0];

				Uri contentUri = null;
				if ("image".equals(type)) {
					contentUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
				} else if ("video".equals(type)) {
					contentUri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
				} else if ("audio".equals(type)) {
					contentUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
				}

				final String selection = "_id=?";
				final String[] selectionArgs = new String[] {
						split[1]
				};

				return getDataColumn(context, contentUri, selection, selectionArgs);
			}
		}
		// MediaStore (and general)
		else if ("content".equalsIgnoreCase(uri.getScheme())) {

			// Return the remote address
			if (isGooglePhotosUri(uri))
				return uri.getLastPathSegment();

			return getDataColumn(context, uri, null, null);
		}
		// File
		else if ("file".equalsIgnoreCase(uri.getScheme())) {
			return uri.getPath();
		}

		return null;
	}

	/**
	 * @param uri The Uri to check. 
	 * @return Whether the Uri authority is ExternalStorageProvider. 
	 */
	private static boolean isExternalStorageDocument(Uri uri) {
		return "com.android.externalstorage.documents".equals(uri.getAuthority());
	}

	/**
	 * @param uri The Uri to check. 
	 * @return Whether the Uri authority is DownloadsProvider. 
	 */
	private static boolean isDownloadsDocument(Uri uri) {
		return "com.android.providers.downloads.documents".equals(uri.getAuthority());
	}

	/**
	 * @param uri The Uri to check. 
	 * @return Whether the Uri authority is MediaProvider. 
	 */
	private static boolean isMediaDocument(Uri uri) {
		return "com.android.providers.media.documents".equals(uri.getAuthority());
	}

	/**
	 * @param uri The Uri to check. 
	 * @return Whether the Uri authority is Google Photos. 
	 */
	private static boolean isGooglePhotosUri(Uri uri) {
		return "com.google.android.apps.photos.content".equals(uri.getAuthority());
	}

	/**
	 * Get the value of the data column for this Uri. This is useful for 
	 * MediaStore Uris, and other file-based ContentProviders. 
	 *
	 * @param context The context. 
	 * @param uri The Uri to query. 
	 * @param selection (Optional) Filter used in the query. 
	 * @param selectionArgs (Optional) Selection arguments used in the query. 
	 * @return The value of the _data column, which is typically a file path. 
	 */
	public static String getDataColumn(Context context, Uri uri, String selection,
									   String[] selectionArgs) {

		Cursor cursor = null;
		final String column = MediaStore.Images.Media.DATA;
		final String[] projection = {
				column
		};

		try {
			cursor = context.getContentResolver().query(uri, projection, selection, selectionArgs,
					null);
			if (cursor != null && cursor.moveToFirst()) {
				final int index = cursor.getColumnIndexOrThrow(column);
				return cursor.getString(index);
			}
		} finally {
			if (cursor != null)
				cursor.close();
		}
		return null;
	}

	/**
	 * 拷贝文件
	 *
	 * @param fromFile
	 *            源文件
	 * @param toFile
	 *            目标文件
	 * @return 是否拷贝成功
	 */
	public static Boolean copyfile(File fromFile, File toFile) {
		Boolean ret = false;
		if (!fromFile.exists()) {
			return ret;
		}

		if (!fromFile.isFile()) {
			return ret;
		}

		if (!fromFile.canRead()) {
			return ret;
		}

		if (!toFile.getParentFile().exists()) {
			toFile.getParentFile().mkdirs();
		}

		if (toFile.exists()) {
			toFile.delete();
		}

		try {
			FileInputStream fisfrom = new FileInputStream(fromFile);
			FileOutputStream fosto = new FileOutputStream(toFile);
			byte[] buffer = new byte[1024];
			int length;
			while ((length = fisfrom.read(buffer)) > 0) {
				fosto.write(buffer, 0, length);
			}
			fisfrom.close();
			fosto.close();
			ret = true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ret;
	}

	/**
	 * 弹出对话框，选择如何获取图片
	 *
	 * @param activity
	 *            activity对象，用于startActivity
	 * @param photoUri
	 *            选择拍照时目标文件的Uri
	 * @param title
	 *            对话框标题文字
	 * @param pickPic
	 *            对话框中“从图库中选择”对应的文字
	 * @param capturePic
	 *            对话框中“拍摄一张照片”对应的文字
	 */
	public static void pickOrCapturePicture(final Activity activity, final Uri photoUri,
											String title, String pickPic, String capturePic) {
		CharSequence[] items = { pickPic, capturePic };
		new AlertDialog.Builder(activity).setTitle(title).setItems(items, new OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				switch (which) {
					case 0: {
						Intent intent = new Intent(Intent.ACTION_GET_CONTENT, null);
						intent.setType("image/*");
						activity.startActivityForResult(intent, REQUEST_CODE_PICK_A_PHOTO);
					}
					break;

					case 1: {
						Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
						intent.putExtra(MediaStore.EXTRA_OUTPUT, photoUri);
						activity.startActivityForResult(intent, REQUEST_CODE_CAPTURE_A_PHOTO);
					}
					break;

					default:
						break;
				}
			}
		}).create().show();
	}

	public static boolean copyPictureFile(Context context, String fromPath, String toPath) {
		if (context == null || fromPath == null || fromPath.length() == 0 || toPath == null || toPath.length() == 0) {
			return false;
		}

		if (!FileUtil.copyfile(fromPath, toPath)) {
			return false;
		}

		File toFile = new File(toPath);
		if (toFile != null && toFile.exists()) {
			context.sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, Uri.fromFile(toFile)));
		}
		return true;
	}
}
