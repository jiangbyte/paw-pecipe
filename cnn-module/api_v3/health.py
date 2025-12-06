from fastapi import APIRouter
from classifier import classifier

router = APIRouter()


@router.get("/health")
async def health_check():
    """
    健康检查端点
    """
    return {
        "status": "healthy",
        "model_loaded": True,
        "classifier_available": True,
        "classes_count": len(classifier.idx_to_label)
    }
