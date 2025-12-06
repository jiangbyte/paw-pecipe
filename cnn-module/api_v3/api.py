from PIL import Image
from fastapi import APIRouter, UploadFile, File, HTTPException, Query
from fastapi.responses import JSONResponse
import requests
from io import BytesIO
from typing import List
from classifier import classifier

router = APIRouter()


def download_image_from_url(url: str) -> Image.Image:
    """
    从URL下载图像

    Args:
        url: 图像URL

    Returns:
        PIL.Image.Image: 下载的图像对象
    """
    try:
        response = requests.get(url, timeout=30)
        response.raise_for_status()
        image = Image.open(BytesIO(response.content)).convert("RGB")
        return image
    except Exception as e:
        raise HTTPException(status_code=400, detail=f"无法从URL下载图像: {str(e)}")


@router.get("/info")
async def get_model_info():
    """
    获取模型信息
    """
    return {
        "model_name": "Vegetable Classifier ONNX",
        "classes_count": len(classifier.idx_to_label),
        "classes": list(classifier.idx_to_label.values()),
        "input_shape": [1, 3, 224, 224],
        "model_loaded": True
    }


@router.post("/upload")
async def predict_upload(
        file: UploadFile = File(...),
        top_k: int = Query(3, description="返回前k个预测结果，默认为3")
):
    """
    上传图片进行预测

    Args:
        file: 上传的图像文件
        top_k: 返回前k个预测结果，默认为3
    """
    try:
        # 读取上传的文件
        contents = await file.read()
        image = Image.open(BytesIO(contents)).convert("RGB")

        # 进行预测
        predicted_label, confidence = classifier.predict(image)
        top_k_results = classifier.predict_with_top_k(image, k=top_k)

        return {
            "filename": file.filename,
            "file_size": len(contents),
            "predicted_class": predicted_label,
            "confidence": confidence,
            "top_k_predictions": [
                {"class": label, "probability": prob}
                for label, prob in top_k_results
            ],
            "success": True
        }
    except Exception as e:
        raise HTTPException(status_code=400, detail=f"预测失败: {str(e)}")


@router.post("/url")
async def predict_url(
        url: str,
        top_k: int = Query(3, description="返回前k个预测结果，默认为3")
):
    """
    通过URL进行预测

    Args:
        url: 图像URL
        top_k: 返回前k个预测结果，默认为3
    """
    try:
        # 从URL下载图像
        image = download_image_from_url(url)

        # 进行预测
        predicted_label, confidence = classifier.predict(image)
        top_k_results = classifier.predict_with_top_k(image, k=top_k)

        return {
            "image_url": url,
            "predicted_class": predicted_label,
            "confidence": confidence,
            "top_k_predictions": [
                {"class": label, "probability": prob}
                for label, prob in top_k_results
            ],
            "success": True
        }
    except Exception as e:
        raise HTTPException(status_code=400, detail=f"预测失败: {str(e)}")


@router.post("/batch")
async def predict_batch(
        files: List[UploadFile] = File(...),
        top_k: int = Query(3, description="返回前k个预测结果，默认为3")
):
    """
    批量预测上传的图片

    Args:
        files: 上传的图像文件列表
        top_k: 返回前k个预测结果，默认为3
    """
    results = []

    for file in files:
        try:
            # 读取上传的文件
            contents = await file.read()
            image = Image.open(BytesIO(contents)).convert("RGB")

            # 进行预测
            predicted_label, confidence = classifier.predict(image)
            top_k_results = classifier.predict_with_top_k(image, k=top_k)

            results.append({
                "filename": file.filename,
                "predicted_class": predicted_label,
                "confidence": confidence,
                "top_k_predictions": [
                    {"class": label, "probability": prob}
                    for label, prob in top_k_results
                ],
                "success": True
            })
        except Exception as e:
            results.append({
                "filename": file.filename,
                "error": f"预测失败: {str(e)}",
                "success": False
            })

    return {
        "batch_size": len(files),
        "results": results
    }
