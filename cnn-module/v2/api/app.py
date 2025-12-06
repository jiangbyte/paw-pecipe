from fastapi import FastAPI
from api import router as api_router
from health import router as health_router


def create_app():
    """创建FastAPI应用实例"""
    app = FastAPI(
        title="蔬菜分类API",
        description="基于ONNX模型的蔬菜图像分类API，支持图片上传和URL识别",
        version="1.0.0"
    )

    # 注册路由
    app.include_router(api_router, prefix="/predict", tags=["prediction"])
    app.include_router(health_router, tags=["health"])

    @app.get("/")
    async def root():
        """
        API根路径
        """
        return {
            "message": "蔬菜分类API",
            "endpoints": {
                "/predict/upload": "上传图片进行预测",
                "/predict/url": "通过URL进行预测",
                "/predict/batch": "批量预测",
                "/info": "模型信息"
            }
        }

    return app


app = create_app()

if __name__ == "__main__":
    import uvicorn

    print("启动FastAPI服务器...")
    uvicorn.run(
        "app:app",
        host="0.0.0.0",
        port=8000,
        reload=True,
        log_level="info"
    )
