/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package edu.uem.sgh.util;

import java.util.concurrent.TimeoutException;

/**
 *
 * @author Kevin Ntumi
 */
public class ThreadUtil {
    public static Exception obterExceptionAtiradaNaBackgroundThread(int startDay, int startHour, int startMinute, int startSeconds, int endDay, int endHour, int endMinute, int endSeconds) {
        Exception e = null;
        
        if ((endDay > startDay) || (endHour > startHour) || (endMinute > startMinute) || (endSeconds - startSeconds >= 2))
            e = new TimeoutException();
        
        return e;
    }
    
    public static void interromperThread(Thread thread) {
        if (!thread.isInterrupted()) {
            try {
                thread.interrupt();
            } catch (Exception e) {
                System.err.println(e);
            }
        }
    }
    
    public static void interromperTodasThreads(int numTentativasMaximas, Thread...threads) {
        if (threads.length == 0) 
            return; 
            
        for (Thread thread : threads) {
            interromperThreadRecursivamente(0, numTentativasMaximas, thread);
        }
    }
    
    public static void interromperThreadRecursivamente(int numTentativa, int numTentativasMaximas, Thread thread) {
        if (numTentativa < 0 || numTentativa > numTentativasMaximas || thread == null || thread.getState() == Thread.State.TERMINATED) return;
        interromperThread(thread);
        interromperThreadRecursivamente(numTentativa + 1, numTentativasMaximas, thread);
    }
}