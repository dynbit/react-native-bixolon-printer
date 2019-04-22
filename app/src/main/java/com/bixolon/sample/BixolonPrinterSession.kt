package com.bixolon.sample

import com.bixolon.sample.consts.Alignment
import jpos.JposException
import jpos.POSPrinter
import jpos.POSPrinterConst
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class BixolonPrinterSession(val posPrinter: POSPrinter) {

    fun close(callback: (Result<Unit>) -> Unit) {
        GlobalScope.launch(Dispatchers.IO) {
            callback(close())
        }
    }
    /**
     * Close Printer Session
     */
    suspend fun close(): Result<Unit> {
        try {
            if (posPrinter.claimed) {
                posPrinter.deviceEnabled = false
                posPrinter.close()
            }
        } catch (e: JposException) {
            e.printStackTrace()
            return Result.failure(e)
        }

        return Result.success(Unit)
    }

    fun printPdf(
            filePath: String,
            page: Int = 0,
            brightness: Int = 50,
            callback: (Result<Unit>) -> Unit
    ) {
        GlobalScope.launch(Dispatchers.IO) {
            callback(printPdf(filePath = filePath, page = page, brightness = brightness))
        }
    }

    suspend fun printPdf(
            filePath: String,
            page: Int = 0,
            alignment: Alignment = Alignment.Left,
            brightness: Int = 50
    ): Result<Unit> {
        try {
            if (!posPrinter.deviceEnabled) {
                return Result.failure("Device is Disabled!")
            }
            val width: Int = posPrinter.recLineWidth
            val station: Int = POSPrinterConst.PTR_S_RECEIPT
            posPrinter.printPDF(station, filePath, width, alignment.value, page, brightness)
        } catch (e: JposException) {
            e.printStackTrace()
            return Result.failure(e)
        }
        return Result.success(Unit)
    }
}