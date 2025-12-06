from fastapi import FastAPI
from api import router as api_router
from fastapi.middleware.cors import CORSMiddleware
from health import router as health_router


def create_app():
    """创建FastAPI应用实例"""
    app = FastAPI()

    # 👇 新增：配置 CORS 中间件
    app.add_middleware(
        CORSMiddleware,
        allow_origins=[
            # "http://localhost:8080",  # UniApp H5 默认端口（Vue CLI）
            # "http://127.0.0.1:8080",
            # "http://localhost:5173",  # Vite 开发服务器常见端口
            # "http://127.0.0.1:5173",
            # "http://localhost:3000",  # React/Vue 常见端口
            # "http://127.0.0.1:3000",
            # "http://192.168.*.*",  # 可选：局域网调试（谨慎使用）
            "*",  # 仅开发环境可用！生产环境请明确指定域名
        ],
        allow_credentials=True,
        allow_methods=["*"],  # 允许所有 HTTP 方法（GET, POST, OPTIONS 等）
        allow_headers=["*"],  # 允许所有请求头（包括 Content-Type, Authorization 等）
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
